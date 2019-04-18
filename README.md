Un articulo más actualizado de este proyecto lo teneis en: [http://www.profesor-p.com/2019/04/18/beans-avanzados-en-spring/](http://www.profesor-p.com/2019/04/18/beans-avanzados-en-spring/)



En esta ocasión he cogido un proyecto de [https://github.com/SimpleProgramming/springboot-servicelocatorfactorybean](https://github.com/SimpleProgramming/springboot-servicelocatorfactorybean) el cual tiene un video en: [https://www.youtube.com/watch?v=rHk5pijFymo](https://www.youtube.com/watch?v=rHk5pijFymo) donde explica como cargar Beans dinámicamente usando Spring.

Imaginemos que tenemos un programa que dependiendo de unos parámetros deba cargar un clase u otro, donde está definida la lógica a seguir.  Por supuesto podemos anidar *condiciones* e instanciar las clases debidas, pero eso tiene un problema y es que si mañana debemos añadir una clase de lógica nueva, deberemos incluir una condición más, y podríamos introducir errores en el código.

Lo ideal seria que tuviéramos una interfaz, y después una serie de clases que implementaran esa interfaz. Después  de alguna manera deberíamos cargar la clase adecuada sin tener una sola condición en el código.

Pues con Spring eso se puede hacer usando la clase **ServiceLocatorFactoryBean**. Esta clase definida en la siguiente API [https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/ServiceLocatorFactoryBean.html](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/config/ServiceLocatorFactoryBean.html) implementa una fabrica de *Beans*, creando un *proxy* de tal manera que invoca a la clase [`BeanFactory`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/BeanFactory.html),  la cual nos permitirá crear nuestros Beans dinámicamente.

Esto que parece bastante confuso explicado así es realmente muy fácil de realizar.

En el ejemplo que tenéis en [https://github.com/chuchip/springboot-servicelocatorfactorybean](https://github.com/chuchip/springboot-servicelocatorfactorybean) lo primero que he creado ha sido una interfaz llamada **AdapterService**, cuyo código como veis a continuación es muy simple:

```java
public interface AdapterService
{
	public String process();
}
```

Se  crean  4 clases que implementan ese interface **BikeService**, **BusService**, **TruckService** y **CarService**. A continuación tenéis el código de **CarService**

```java
@Service("Car")
public class CarService implements AdapterService {
	int numberExecution=1;
	
	@Override
	public String process() {		
		return "inside car service -  number of executions: "+(numberExecution++);
	}
}
```

Lo importante de esta clase es la etiqueta `@Service("car")` pues especificamos el nombre con el que deberemos cargar la clase.

En las demás clases existen las etiquetas:  `@Service("Bike")`, `@Service("Bus")` y `@Service("Truck")`.

Ahora se debe crear el interface que pasaremos a la clase **ServiceLocatorFactoryBean** en este ejemplo es la clase **ServiceRegistry**

````java
public interface ServiceRegistry {
	public AdapterService getService(String serviceName);
}
````

En esta clase deberemos definir la función `getService` que es la que **ServiceLocatorFactoryBean**  invocara. El nombre de esta función debe ser **getService** y debe recibir un parámetro que será el nombre del *bean* (el definido con las etiquetas `@service`), además devolverá un objeto que implemente  el interfaz **AdapterService**.

En la clase **VehicleConfig** definimos la función que nos devolverá el **FactoryBean**

```java
@Configuration
public class VehicleConfig {
	@Bean
	public FactoryBean<?> factoryBean() {
		final ServiceLocatorFactoryBean bean = new ServiceLocatorFactoryBean();
		bean.setServiceLocatorInterface(ServiceRegistry.class);
		return bean;
	}
}
```

Simplemente, en ella, instanciamos una clase tipo **ServiceLocatorFactoryBean**,  especificamos el interface que debe devolver y luego devolvemos ese objeto **ServiceLocatorFactoryBean** que por supuesto implementa el interfaz **FactoryBean** 

Para ver la funcionalidad de estos beans, en el proyecto de ejemplo, hemos creado un controlador **rest** muy simple.

```
@RestController
public class VehicleController {
	@Autowired
	private ServiceRegistry serviceRegistry;
	
	@GetMapping("{vehicle}")
	public String  processGet(@PathVariable String vehicle) {
		return serviceRegistry.getService(vehicle).process();
	}
}
```

En ella, dependiendo de la URL llamada,  que será volcada en la variable `vehicle` se llamara a una de las clases que implementan el interfaz **AdapterService ** y todo ello sin un solo `if`

Así si ejecutamos:

```
$ curl  -s  http://localhost:8080/Car
inside car service -  number of executions: 1
$ curl  -s  http://localhost:8080/Car
inside car service -  number of executions: 2
```

se puede observar como es cargada la clase `CarService`y como se mantiene en el contexto de *Spring*, pues se puede ver como el numero de ejecuciones aumenta con cada llamada.

Si ejecutamos las siguientes sentencias:

```
$ curl  -s  http://localhost:8080/Truck
inside truck service -  number of executions: 1
$ curl  -s  http://localhost:8080/Truck
inside truck service -  number of executions: 2
$ curl  -s  http://localhost:8080/Car
inside car service -  number of executions: 3
```

podemos observar como funciona todo correctamente.

Por supuesto si intentamos cargar un *Bean* no definido dará un error:

```
$ curl  -s  http://localhost:8080/Boat
{"timestamp":"2019-04-18T21:07:23.139+0000","status":500,"error":"Internal Server Error","message":"No bean named 'Boat' available","path":"/Boat"}
```

Y de esta manera tan simple. gracias a la magia de Spring, se pueden crear programas fácilmente ampliables y modulares.

Hasta otra y no olvidéis seguirme en [Twitter](https://twitter.com/chuchip) para estar al tanto de nuevas entradas de este blog.