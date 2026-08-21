# Play Console submission checklist

## Ready to enter

- App name: `Ge’ez Numerals`
- Default language: English (United States)
- App or game: App
- Free or paid: Free
- Category: Tools
- Package name: `com.likuamare.geeznumerals`
- Version: `1.0.0` (`versionCode` 1)
- Minimum Android: 7.0 / API 24
- Target Android: API 36
- Ads: No
- App access: All functionality is available without special access
- Data collected or shared: None
- Network access: None
- Target age groups: 13 and older
- Designed for children: No
- Developer support email: `samimanamare@gmail.com`

## Files to upload

- App icon: `assets/app-icon-512.png`
- Feature graphic: `assets/feature-graphic-1024x500.png`
- Phone screenshot 1: `assets/screenshot-1-conversion-1080x1920.png`
- Phone screenshot 2: `assets/screenshot-2-privacy-1080x1920.png`
- Signed bundle: `../app/build/outputs/bundle/release/app-release.aab`
- Listing text: `listing-en.md`

## Before production submission

1. Enable GitHub Pages for the repository’s `/docs` folder so `docs/privacy-policy.html` has a
   public, stable HTTPS URL; paste that URL into App content.
2. Sign the bundle with the developer’s private upload key and verify it with `jarsigner`.
3. Upload first to Internal testing, install the Play-generated build, and run one conversion.
4. Complete the content-rating questionnaire using the answers in `listing-en.md`.
5. Review the automated pre-launch report, then promote the same build to Production.

The privacy and data-safety answers are valid only while the app remains offline and has no
third-party SDKs, analytics, advertising, accounts, or network permissions.
