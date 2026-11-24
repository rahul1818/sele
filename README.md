# QAMind Selenium Hub Demo

Standalone **Selenium + TestNG** project (not part of qamind.ai).  
Two environments — push this folder to your GitHub, then connect it in QAMind Hub.

## Environments

| Env | Site | Hub artifact |
|-----|------|--------------|
| **staging** | https://www.saucedemo.com | `selenium-report-uat` |
| **production** | https://the-internet.herokuapp.com/login | `selenium-report` |

## Push to GitHub

```powershell
cd $env:USERPROFILE\Desktop\selenium-hub-demo
git init
git add .
git commit -m "Selenium Hub demo with staging and production"
git branch -M main
git remote add origin https://github.com/YOUR_USER/YOUR_REPO.git
git push -u origin main
```

## Run locally

```powershell
mvn -B clean test -Denv=staging -Dheadless=true
mvn -B clean test -Denv=production -Dheadless=true
```

Needs Java 17+, Maven, Chrome.
