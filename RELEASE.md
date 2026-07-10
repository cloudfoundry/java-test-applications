# Releasing java-test-applications

## Snapshot releases (automated)

A GitHub Actions workflow (`.github/workflows/snapshot-release.yml`) builds all
applications on every push to `main` and publishes a rolling pre-release tagged
`v1.0.0-SNAPSHOT`. Artifacts are replaced on each push.

You can also trigger it manually from any branch via **Actions → Run workflow**.

### Artifacts published

| Artifact | Description | Java | Spring Boot |
|----------|-------------|------|-------------|
| `java-main-application-*.jar` | Standalone main-class app | 21 | 4.1 |
| `java-task-application-*.jar` | CF run-task app | 21 | 4.1 |
| `web-application-*.war` | Embedded Tomcat WAR | 21 | 4.1 |
| `ejb-application-*.war` | Jakarta EE 10 EJB WAR | 21 | 4.1 |
| `dist-zip-application-*.zip` | Distribution zip (scripts + libs) | 21 | 4.1 |
| `java-main-application-boot3-*.jar` | Standalone main-class app | 17 | 3.5 |

---

## Fixed releases (manual)

To publish a numbered release (e.g. `1.0.0`):

1. **Update versions** in both build files:

   `build.gradle` (root — applies to all multi-module subprojects):
   ```groovy
   version = '1.0.0'
   ```

   `java-main-application-boot3/build.gradle`:
   ```groovy
   version = '1.0.0'
   ```

2. **Update manifest paths** in each `manifest.yml` if the version is hardcoded in the `path:` field.

3. **Commit, tag, and push:**
   ```bash
   git add -A
   git commit -m "chore: release 1.0.0"
   git tag v1.0.0
   git push origin main --tags
   ```

4. **Trigger the release workflow** manually via **Actions → Run workflow** on the `v1.0.0` tag,
   or add a tag trigger to the workflow:
   ```yaml
   on:
     push:
       tags:
         - 'v*'
   ```
   When using a tag trigger the release will use the tag name instead of `v1.0.0-SNAPSHOT`
   and should be marked `prerelease: false`.

---

## Local build

Requires Java 21 (multi-module) and Java 17 (`java-main-application-boot3`).

```bash
# Multi-module (Java 21)
./gradlew assemble

# Standalone boot3 module (Java 17)
cd java-main-application-boot3
./gradlew bootJar
```
