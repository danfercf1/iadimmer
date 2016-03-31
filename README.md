# IA DIMMER

##Compilar

javac -classpath lib\jade.jar -d classes src\ia\Blackboard.java

##Ejecutar

java -cp lib\jade.jar;classes jade.Boot -gui -agents blackboard:Blackboard.Blackboard

##Integracion

En DimmerAgent.java esta el codigo para realizar la comunicacion entre un agente y el Blackboard (Pizarra).<br>
Lo que se tendria que hacer es integrar entre esa clase y lo que se tiene de la clase de logica difusa.<br>
En la funcion setup de DimmerAgent esta el ejemplo del funcionamiento del envio de mensaje hacia el blackboard.

##Blackboard

La clase Blackboard es la pizarra, recibe un mensaje tipo ACL.REQUEST y lo guarda en un diccionario y cada vez que recibe un nuevo mensaje lo imprime en pantalla junto con el numero de mensajes guardados.


##Agregar nuevos agentes a la plataforma

Para agregar nuevos agentes se usara la interfaz grafica, agregando los nuevos agentes de la clase DimmerAgent, (Pienso que podriamos crear varias clases por cada agente o solo una creando varios).
