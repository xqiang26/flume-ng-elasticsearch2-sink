# Flume 1.7.0 supporting ElasticSearch 2.4.4

This version of the Flume ElasticSearch sink works with ElasticSearch 2.4.4
but to do so, **some libreries used by Flume had to be updated to the newest 
versions**, for example: elasticsearch (it's obvious) or guava (from 11.0.2 
to 19.0). **For this reason, many tests do not work retuning a pretty "jar 
hell" exception.**

As Flume is packaged right now (sink and flume libraries in the  same class 
loader) seems dificult to find another solution. The best way, without 
modifying Flume, would be a new ElasticSearch sink based on http API and 
built on top of a reactive framework.

## Modifications to your Flume lib path:
    
### Libraries to be added:
- t-digest-3.0.jar
- lucene-core-5.4.1.jar
- jsr166e-1.1.0.jar
- jackson-dataformat-yaml-2.6.2.jar
- jackson-dataformat-smile-2.6.2.jar
- jackson-dataformat-cbor-2.6.2.jar
- jackson-core-2.6.2.jar
- hppc-0.7.1.jar
- guava-19.0.jar
- flume-ng-elasticsearch-sink-1.7.0.jar
- elasticsearch-2.4.4.jar
- compress-lzf-1.0.2.jar

### Libraries to be removed:
- lucene-core-4.10.4.jar
- jackson-mapper-asl-1.9.3.jar
- jackson-databind-2.3.1.jar
- jackson-core-asl-1.9.3.jar
- jackson-core-2.3.1.jar
- jackson-annotations-2.3.0.jar
- guava-11.0.2.jar
- flume-ng-elasticsearch-sink-1.6.0.jar
- elasticsearch-1.7.3.jar
