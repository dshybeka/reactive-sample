~/prg/graalvm-1.0.0-rc1/bin/native-image  --no-server  -Dio.netty.noUnsafe=true  -H:ReflectionConfigurationFiles=./reflectconfigs/netty.json  -H:+ReportUnsupportedElementsAtRuntime  -Dfile.encoding=UTF-8  -jar native-1.0-SNAPSHOT.jar