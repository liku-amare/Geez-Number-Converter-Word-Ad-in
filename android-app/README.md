# Ge’ez Numerals for Android

A minimal offline Android application that converts positive decimal integers to traditional
Ethiopic numerals.

## Design constraints

- Native Java and standard Android SDK widgets only.
- No runtime dependencies.
- No permissions, network access, analytics, advertising, accounts, or backend.
- Android 7.0 (API 24) minimum; Android 16 (API 36) target.

## Conversion specification

The converter is an independent implementation of the W3C `ethiopic-numeric` counter algorithm.
The encoded characters are defined in the Unicode Ethiopic block.

- W3C algorithm: https://www.w3.org/TR/css-counter-styles-3/#ethiopic-numeric
- Unicode Ethiopic chart: https://www.unicode.org/charts/PDF/U1200.pdf

Traditional Ethiopic numerals represent positive non-zero integers. The app rejects zero,
negative values, fractions, and non-decimal input.

## Verify the converter

```sh
./verify-converter.sh
```

## Build

```sh
./gradlew clean lint bundleRelease
```

The release bundle is written under `app/build/outputs/bundle/release/`.

## Sign the Play bundle

Google Play requires the app bundle to be signed with an upload key. For a new app, let Google
Play generate and protect the separate app-signing key.

1. Create or select an RSA upload key stored in `upload-keystore.jks`.
2. Copy `keystore.properties.example` to `keystore.properties` and enter the four local values.
3. Run `./gradlew clean lint bundleRelease` again.
4. Verify the result with `jarsigner -verify app/build/outputs/bundle/release/app-release.aab`.

The keystore and properties file are ignored by Git. Back up the upload key and its passwords in
a password manager; do not commit or send them in chat.

On macOS, `sign-with-keystore-macos.sh` is the shortest secure path for an already-built bundle.
It prompts through hidden native dialogs, auto-detects a single key alias, and keeps passwords out
of files and terminal output:

```sh
./sign-with-keystore-macos.sh /path/to/upload-key.jks \
  app/build/outputs/bundle/release/app-release.aab \
  /path/to/geez-numerals-v1.0.0.aab
```

## Play listing pack

`store-listing/` contains the listing copy, privacy policy, submission answers, 512 px icon,
1024 × 500 feature graphic, and two genuine 1080 × 1920 API 36 screenshots.

The same privacy policy is available at `../docs/privacy-policy.html`, ready to host through GitHub
Pages without adding a separate website or service.
