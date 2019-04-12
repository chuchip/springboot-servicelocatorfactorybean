# springboot-servicelocatorfactorybean
A simple example explaining how to make use of service locator factory bean

Al realizar la siguiente llamada:
```
curl -s -X POST   http://localhost:9001/vehicle   -H 'Content-Type: application/json'   -d '{
    "vehicleName": "Renault",
    "vehicleType": "Car"
}'
```

según el valor del parametro `vehicleType` se invocara a diferentes Clases, sin usar ningun "if", 
pues se usara la clase ServiceLocatorFactoryBean:


En este ejemplo se ejecuta el metodo process del interface **AdapterService** el cual es implementado por las clases 
**BikeService**, **BusService**, **TruckService** y **CarService*


Hay un video en la pagina: [https://www.youtube.com/watch?v=rHk5pijFymo](https://www.youtube.com/watch?v=rHk5pijFymo)
donde explica el funcionamiento (en Ingles)
