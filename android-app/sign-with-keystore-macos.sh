#!/bin/zsh

set -euo pipefail
umask 077

if (( $# != 3 )); then
    print -u2 "Usage: $0 <keystore.jks> <unsigned.aab> <signed.aab>"
    exit 2
fi

KEYSTORE_PATH=$1
INPUT_BUNDLE=$2
OUTPUT_BUNDLE=$3
OUTPUT_CREATED=0

cleanup() {
    local exit_code=$?
    unset STORE_PASSWORD KEY_PASSWORD KEY_INFO
    if (( exit_code != 0 && OUTPUT_CREATED == 1 )) && [[ -f "$OUTPUT_BUNDLE" ]]; then
        unlink "$OUTPUT_BUNDLE"
    fi
    exit $exit_code
}
trap cleanup EXIT

if [[ ! -f "$KEYSTORE_PATH" ]]; then
    print -u2 "Keystore not found: $KEYSTORE_PATH"
    exit 2
fi
if [[ ! -f "$INPUT_BUNDLE" ]]; then
    print -u2 "Unsigned bundle not found: $INPUT_BUNDLE"
    exit 2
fi
if [[ -e "$OUTPUT_BUNDLE" ]]; then
    print -u2 "Refusing to overwrite existing output: $OUTPUT_BUNDLE"
    exit 2
fi

STORE_PASSWORD=$(/usr/bin/osascript <<'APPLESCRIPT'
set response to display dialog "Enter the upload keystore password. It stays local and is not saved." default answer "" with hidden answer buttons {"Cancel", "Continue"} default button "Continue" cancel button "Cancel" with title "Sign Ge’ez Numerals"
return text returned of response
APPLESCRIPT
)
export STORE_PASSWORD

KEY_INFO=$(keytool -list -v -keystore "$KEYSTORE_PATH" -storepass:env STORE_PASSWORD)
UPLOAD_ALIAS=$(print -r -- "$KEY_INFO" | awk -F': ' '/^Alias name: / {print $2}')
ALIAS_COUNT=$(print -r -- "$UPLOAD_ALIAS" | awk 'NF {count++} END {print count+0}')

if [[ "$ALIAS_COUNT" != "1" ]]; then
    print -u2 "Expected exactly one keystore alias, found $ALIAS_COUNT."
    exit 2
fi
if ! print -r -- "$KEY_INFO" | grep -q '^Entry type: PrivateKeyEntry'; then
    print -u2 "The keystore entry is not a private signing key."
    exit 2
fi
unset KEY_INFO

KEY_PASSWORD=$(/usr/bin/osascript <<'APPLESCRIPT'
set response to display dialog "Enter the key password. If you used one password when creating the keystore, enter the same password again." default answer "" with hidden answer buttons {"Cancel", "Continue"} default button "Continue" cancel button "Cancel" with title "Sign Ge’ez Numerals"
return text returned of response
APPLESCRIPT
)
export KEY_PASSWORD

cp "$INPUT_BUNDLE" "$OUTPUT_BUNDLE"
OUTPUT_CREATED=1

jarsigner \
    -sigalg SHA256withRSA \
    -digestalg SHA-256 \
    -keystore "$KEYSTORE_PATH" \
    -storepass:env STORE_PASSWORD \
    -keypass:env KEY_PASSWORD \
    "$OUTPUT_BUNDLE" \
    "$UPLOAD_ALIAS"

jarsigner -verify "$OUTPUT_BUNDLE"
print "Signed bundle created: $OUTPUT_BUNDLE"
