collaborative-whiteboard
========================

Collaborative-whitebord consiste em um projeto com intuito de auxiliar no processo de desenvolvimento de software. 

Construindo o projeto para ambientes de desenvolvimento:

Dependencias para desenvolvimento do projeto.

 - Wildfly 9.0
 - Java 8
 - Maven 3 
 - Postgresql 9.4
 - Property Monitor : git@github.com:drferreira/property-monitor.git
 - Equalize Object  : git@github.com:drferreira/equalizer-object.git

Processo de construção.

1º - (Clonar Projeto) - Branch DEV

2º - (Clonar | Instalar Dependencias)

3º - $ cd collaborative-whiteboard/cw-root

4º - $ mvn clean install -Ddatabase.action=true

- Start Wildfly

5º - $ cd collaborative-whiteboard/cw-ear

6º - $ mvn wildfly:deploy


