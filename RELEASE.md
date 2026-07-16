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

## Fixed releases (via GitHub Actions)

Two ways to trigger a fixed release:

**Option A — push a git tag** (fully automated):
```bash
git tag v1.0.0
git push origin v1.0.0
```
The workflow triggers automatically, derives version from the tag, builds, and publishes the release.

**Option B — manual dispatch** (no tag needed):
1. Go to **Actions → Publish Fixed Release → Run workflow**
2. Enter the version in `X.Y.Z` format (e.g. `1.0.0`)
3. Click **Run workflow**

Both paths:
- Build all modules with `-Pversion=<version>` (no file changes needed)
- Run tests as a quality gate
- Create a GitHub release tagged `v<version>` with all artifacts attached

> **Note:** Run from `main` (or the branch/commit you want to release).
> The version is applied at build time only — source files are not modified.

### Manual alternative (without GitHub Actions)

If you need to release without the workflow:

1. **Commit, tag, and push** (pushing the tag triggers the workflow automatically):
   ```bash
   git tag v1.0.0
   git push origin v1.0.0
   ```

2. **Or build locally** and upload artifacts by hand:
   ```bash
   # Multi-module (Java 21)
   ./gradlew build -Pversion=1.0.0

   # Standalone boot3 module (Java 17)
   cd java-main-application-boot3
   ./gradlew bootJar -Pversion=1.0.0
   ```

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
