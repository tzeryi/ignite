* {IGNITE_HOME}\examples\config\example-default.xml �����]�w H2 datasource
       �p�G pom.xml �̭��S���[�J h2 driver ����, �|�L�k����
* Ignite �w�]�� cacheMode �O PARTITIONED, ���F�קK�{�����浲���� cache ������ƿ�, �i�H
    1. �N�{���ݪ� clientMode �]�w�� true
    2. �N�{���ݪ� cacheMode �]�w�� REPLICATED
    3. enable PARTITIONED �Ҧ����ƥ��\�� <property name="backups" value="1"/>
* �� {IGNITE_HOME}\libs\optional ����  ignite-rest-http �ƻs��   {IGNITE_HOME}\libs\ �U��, 
       �A����   {IGNITE_HOME}\bin\ingite.{bat|sh}, �Y�i�ҥ� ignite rest �\��
* �p�G�n�b server �ݱҥ� datasource ����, ������ jdbc driver jar ���  {IGNITE_HOME}\libs �U
* �ϥ�   {IGNITE_HOME}\bin\ignite-schema-import.{bat|sh} �i�H���ͻݭn�� pojo class ��  configuration
* set IGNITE_HOME={IGNITE_HOME}

  {IGNITE_HOME}\bin\ignite.{bat|sh} examples/config/example-ignite.xml