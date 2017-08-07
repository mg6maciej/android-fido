1. To build Signed APK with the `fido-example.keystore`:

```
./gradlew assembleRelease \
  -Pandroid.injected.signing.store.file=fido-example.keystore \
  -Pandroid.injected.signing.store.password=fidou2f \
  -Pandroid.injected.signing.key.alias=fidoexamplekey \
  -Pandroid.injected.signing.key.password=fidou2f
```

2. Find the Signed APK at app/build/outputs/apk/app-release.apk and install:
adb install app/build/outputs/apk/app-release.apk

The APK signed by above key will be able to talk to demo server
u2fdemo.appspot.com for an end-to-end demonstration of U2F Security Key
registration and authentication flows.