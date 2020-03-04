Developer Guide
===============

Local build
--------------------------------
1. setup environemnt
```bash
cd ./pandaria
docker-compose up -d
```

2. run local build
```bash
./gradlew clean test
```

Upload Archives
---------------
```bash
./gradlew uploadArchive -x :doc:uploadArchive
```
