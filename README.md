collaborative-whiteboard
========================

Collaborative-whitebord consiste em um projeto com intuito de auxiliar no processo de desenvolvimento de software. 

Construindo o projeto para ambientes de desenvolvimento:

Dependencias para desenvolvimento do projeto.

 * Wildfly 9.0
 * Java 8
 * Maven 3 
 * Postgresql 9.4
 * Property Monitor : git@github.com:drferreira/property-monitor.git
 * Equalize Object  : git@github.com:drferreira/equalizer-object.git

## Processo de construção.

(Clonar Projeto) - Branch DEV

(Clonar | Instalar Dependencias)

```{r, engine='bash', count_lines}
cd collaborative-whiteboard/cw-root
mvn clean install -Ddatabase.action=true -P public
```

## Start Wildfly

```{r, engine='bash', count_lines}
cd collaborative-whiteboard/cw-ear
mvn wildfly:deploy -P public
```


