# Trabajo Práctico Final

**DISEÑO DE BASES DE DATOS - MG IS 2021**



Se presenta en este documento el trabajo práctico final del curso, el cuál consiste en persistir un modelo de objetos en dos tipos diferentes de bases de datos: relacional (utilizando MySQL) y basada en documentos (utilizando MongoDB). El proyecto representa un sistema de deliverys de productos ofrecidos por distintos proveedores.

Se les proporcionará un proyecto en Java ya inicializado, el cual se trata de una API REST, utiliza el framework Spring y Maven como gestor de dependencias. El proyecto cuenta ya con una arquitectura establecida, similar a las vistas en otros proyectos del curso, así como también el modelo de clases y relaciones ya creado.

### Tecnologias necesarias

Para el desarrollo del proyecto dado, serán necesarias de las siguientes tecnologías: • Java JDK8

- Maven
  Como gestor de dependencias
- MySQL v5.7 en adelante
  Como base de datos relacional
- MongoDB
  Como base de datos orientada a documentos
- GitHub
  Como software de control de versiones

Se recomienda utilizar IntelliJ como IDE, aunque puede usarse el de su preferencia.

Para la instalación de las herramientas mencionadas puede utilizar la guía publicada al inicio del curso.

La utilización de contenedores Docker es opcional.

### Descripción de la aplicación

El modelo a persistir se trata de un sistema de delivery similar a soluciones existentes en el mercado (como PedidosYa, Rappi, etc.). Dicho modelo posee el siguiente diagrama de clases:

![Diagrama de clases](https://github.com/fedediclaudio/tpfinal_bd/blob/main/Diragrama%20de%20clases.png)

La aplicación dispone de un conjunto de usuarios (*User*) que pueden ser de dos tipos: repartidores (*DeliveryMan*), los cuales se encargan de llevar los pedidos generados por, el segundo tipo de usuarios, los clientes (*Client*). Cada usuario posee, además de ciertos datos propios del usuario, un atributo que determina si está activo (*active*) en el sistema, así como también un puntaje (*score*). Este puntaje se calcula de diferente manera dependiendo del tipo de usuario: un cliente suma un punto por cada pedido finalizado y resta un punto cuando cancela uno ya confirmado y asignado; un repartidor suma un punto cuando completa una entrega mientras que resta dos puntos cuando rechaza un pedido que le fue asignado.

Cada cliente posee un conjunto de direcciones (*Address*) guardadas en el sistema. Cuando este genera un pedido nuevo debe elegir una de sus direcciones como lugar de entrega.

Las órdenes (*Order*) poseen, además de sus datos básicos, un estado (*OrderState*), que irá cambiando a medida que la entrega avanza. Este estado, define el comportamiento dinámico ante las diferentes acciones que los usuarios pueden realizar sobre un pedido. Inicialmente, el estado del pedido será pendiente (*Pending*). Una vez confirmado por el cliente, el pedido se asigna a un repartidor libre y pasa a un estado de asignado (*Assigned*). Dicho repartidor puede rechazar el pedido (descontando su puntaje), en cuyo caso el estado pasa a un estado de cancelado (*Cancelled*), o puede aceptarlo y comenzar con el reparto del mismo, así este último pasa a un estado de en proceso (*Sent*). Una vez entregado, el pedido pasa a un estado de finalizado (*Finished*) y se actualizan los puntajes. Antes de que el pedido fuera aceptado por el repartidor, este puede ser cancelado por el cliente en cualquier momento, llevándolo a un estado de cancelado (*Cancelled*). El siguiente diagrama muestra de una manera gráfica la transición de estados de un pedido:

![Estados](https://github.com/fedediclaudio/tpfinal_bd/blob/main/State.png)

Cada orden se compone de una serie de items (*Item*), cada uno se trata de un producto solicitado con una cantidad del mismo. Opcionalmente el cliente puede agregar una descripción o aclaración sobre el producto ordenado.

Una vez finalizado el pedido el cliente puede generar una calificación (*Qualification*) en la cual podrá establecer un puntaje (de una a cinco estrellas) y una opinión en forma de texto.

El sistema también modela a los proveedores de los productos (*Supplier*), mediante los datos básicos de dichos proveedores, junto con una dirección y un calificación, la cual será el promedio entre las calificaciones recibidas por los clientes. Además, cada proveedor posee un tipo (*SupplierType*) que indica si se trata, por ejemplo, de un restaurant, heladería, quiosco, etc.

Cada proveedor ofrece una serie de productos (*Product*), de los cuales se registra nombre, precio, peso, una descripción y el tipo de producto que se trata (*ProductType*). Cada producto es exclusivo de un proveedor y un producto puede tener muchos tipos. Tanto el tipo de producto como el tipo de proveedor es necesario en la aplicación para la realización de búsquedas.

La aplicación necesita mantener un historial de los precios de cada producto (*HistoricalProductPrice*), para ello registra los valores que fue presentando entre las distintas fechas.

La aplicación utiliza el patrón State para implementar el estado de las órdenes, si no conoce de dicho patrón le dejamos un link de utilidad: https://refactoring.guru/es/design-patterns/state

### Configuracion

Para poder persistir los objetos deberá configurar la conexión de su proyecto con la correspondiente base de datos; para ello puede utilizar como guía los proyectos vistos durante el desarrollo del curso.

Las dependencias necesarias, tanto para el mapeo en MySQL como en MongoDB, se encuentran ya cargadas dentro del archivo de dependencias “POM.xml”, pero se encuentran comentadas. Según cuál va a utilizarse, descomente las correspondientes.

### Actividad

Se deberá entregar el proyecto en dos resoluciones: una con persistencia de objetos a MySQL (base de datos relacional) y otra a MongoDB (base de datos orientada a documentos). Para ello se deberá implementar el mapeo mediante anotaciones en las clases, y deberá implementar los servicios y los repositorios necesarios.

El proyecto entregado deberá tener en cuenta los siguientes puntos:

* Utilizar relaciones bidireccionales en los casos que están sean necesarias.

- Determinar la carga bajo demanda de objetos relacionados (o lazy) y las operaciones en

  cascada sobre estas.

- Determinar el mapeo de objetos relacionados a documentos embebidos o referencias

  en MongoDB.

- Uso de transacciones en el servicio.

- Implementación del control de concurrencia mediante el versionado de objetos en las clases necesarias.

Se deberán implementar en la aplicación endpoints para realizar las siguientes acciones: • Agregar un ítem a una orden ya creada.

- Confirmar un pedido. Esto implica buscar un repartidor libre y asignarle dicho pedido.

- Añadir una calificación a una orden ya completada. Tenga en cuenta que deberá

  actualizar la calificación del proveedor.

- Actualizar los datos de un producto. Tenga en cuenta que puede cambiar su precio.

- Eliminar un producto de los ofrecidos por un proveedor.

- Obtener todos los proveedores de un cierto tipo.

- Obtener todos los productos y su tipo, de un proveedor específico.

- Obtener las órdenes con más productos de un proveedor específico.

- Obtener la orden de mayor precio total de un día dado.

- Obtener los diez repartidores con mayor puntaje.

- Obtener los diez proveedores que más órdenes despacharon.

- Obtener los precios de un producto entre dos fechas dadas.

- Obtener el precio promedio de los productos de cada tipo, para todos los tipos.

- Obtener la información de los proveedores que tengan al menos una calificación de unaestrella (la más baja). Es necesario también el número de estas calificaciones que el

  proveedor posee.

- Obtener los proveedores que ofrezcan productos de todos los tipos.

El uso de test para verificar el correcto funcionamiento del servicio creado es opcional.

El proyecto deberá estar acompañado por un informe en el cual se describa las resoluciones empleadas para los anteriores puntos, así como también las decisiones tomadas en las persistencias de objetos.

### Forma de entrega

El proyecto será compartido en un repositorio de Github, sobre el cual cada grupo dispondrá de dos ramas: una en la cual deberá estar el proyecto mapeado a MySQL y otra donde deberá estar mapeado a MongoDB.

Será necesario que cada grupo comunique los usuarios de Github de cada integrante para poder asignarle un número de grupo y compartirles dichas ramas, las cuales serán de acceso solo por parte de los integrantes de cada grupo.

Fecha límite de entrega: 31 de Junio de 2022

# Consideraciones en el desarrollo y la ejecuci&oacute;n del proyecto

### Como levantar la BD en cada branch

En la raíz del proyecto principal se disponibiliza un directorio docker con un archivo docker-compose.yml para cada ambiente. En el caso de la BD **JPA**, también se disponibilizan dos scripts sql: 

1. schema.sql 
2. data.sql

El primero crea el schema de la BD y el usuario que está configurado en el link de conexión a la BD dentro del archivo de configuración .properties.
El segundo está vacío, y se usaría en el caso de que se requiera ejecutar alguna secuencia de datos.

En el caso de la BD **MongoDB**, se levanta y se disponibiliza la BD en el puerto por defecto, y no necesita de configuraciones adicionales.

Una vez que se levanta la BD, al correrse la aplicación el sistema carga datos de prueba para poder trabajar. Estos datos se generan en la clase **com.bd.tpfinal.config.DataInitialization**, y la cantidad de datos generados se puede configurar modificando los valores en el archivo ***application.properties***
```
data.initialization.number_of_orders=100
data.initialization.number_of_clients=100
data.initialization.number_of_products=200
data.initialization.number_of_delivery=10
```

### Colecciones de Postman de ejemplo

De nuevo, en el directorio raíz de cada branch se disponibiliza una colección de postman en formato json (***TPFinal-BD.postman_collection.json***), que puede importarse y utilizarse. Existen algunos datos de ejemplo que deben reemplazarse. Los datos en los endpoints son de ejemplo pero pueden utilizarse. 

### Swagger

La documentación de la API se realizó utilizando swagger. Se puede consultar el endpoint por defecto.
En el caso de este proyecto, el enpoint sería http://localhost:8081/swagger-ui/index.html

### Diseño de la aplicación

En principio comencé a desarrollar la aplicación utilizando un diseño de arquitectura limpia. Luego consulté con la cátedra y me fue indicado que el modelo que se debía seguir es el propuesto, con una arquitectura de tres capas.
Como ya tenía bastante desarrollado, pude reutilizar varias de las clases desarrolladas. El modelo sigue el siguiente diseño:

```
controller -> service -> proxy-repository -> repository
```
La comunicación se realiza a través de interfaces en cada capa, y se realiza un mapeo desde el obteto de transferencia de datos (dto) al modelo de datos (entidades).
El diseño no sigue un estricto modelo REsT, ya que por ejemplo, al crear/actualizar un recurso se debe retornar el recurso. En cambio, se devuelve un mensaje donde además del recurso se retorna un status y un mensaje descriptivo. Un ejemplo de esto es la siguiente respuesta:

```
{
    "status": "OK_200",
    "message": "Order address updated.",
    "data": {
        "id": "101",
        "number": 10989985,
        "status": "PENDING",
        "client": {
            "name": "NAHIRÑAK Celia",
            "score": 0,
            "client_id": "9"
        },
        "address": {
            "id": "23",
            "address": "[NAHIRÑAK Celia] Address='MILTON Nro. 497 - Buenos Aires', apartment='null', coords=null, description='null"
        },
        "items": [
            {
                "price": 169.903,
                "item_id": "830",
                "order_id": "101",
                "product_id": "95",
                "product_name": "psietaípsilon",
                "quantity": 20
            },
            {
                "price": 169.903,
                "item_id": "831",
                "order_id": "101",
                "product_id": "95",
                "product_name": "psietaípsilon",
                "quantity": 20
            }
        ],
        "date_of_order": "2022-05-24T21:07:15.000+00:00",
        "total_price": 6796.12,
        "qualification_score": 0.0
    }
}
```

Se disponibilizan los siguientes controladores con sus métodos:

| Controlador         | Método HTTP | Endpoint                                     | Descripción                                                                                                                                | Parámetros                                                                                                                                                                                                                            |
|---------------------|-------------|----------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| ClientsController   | GET         | /clients                                     | Obtiene todos los clientes                                                                                                                 ||
| ClientsController   | POST        | /clients                                     | Crea un nuevo cliente                                                                                                                      ||
| ClientsController   | PUT         | /clients/{id}                                | Actualiza los datos de un cliente                                                                                                          ||
| ClientsController   | GET         | /clients/{client_id}                         | Obtiene los datos de un cliente por ID                                                                                                     ||
| DeliveryController  | POST        | /delivery                                    | Crea un nuevo repartidor                                                                                                                   ||
| DeliveryController  | GET         | /delivery/{delivery_man_id}                  | Obtiene un repartidor por ID                                                                                                               ||
| DeliveryController  | GET         | /delivery                                    | Obtiene todos los repartidores, o los que cumplen con un criterio. Los criterios (parámetros) son excluyentes.                             | -`?delivery_man_id=`  filtra por id <br/>- `?best_ranked=` filtra a los 10 mejor ranqueados <br/>-`?free=` obtiene los repartidores libres                                                                                            |
| OrderController     | GET         | /orders                                      | Obtiene todas las órdenes, o las que cumplan con uno de los criterios seleccionados.                                                       | -`?status=` obtiene las órdenes por estado. <br/>-`?number=` obtiene la órden por número de órden.<br/>Si no se especifican parámetros, retorna todas las órdenes.                                                                    |
| OrderController     | GET         | /orders/{order_id}                           | Obtiene una órden por ID                                                                                                                   ||
| OrderController     | GET         | /orders/{client_id}/address/{address_id}"    | Crea una nueva órden para un cliente, con una dirección. Se puede agregar también un comentario para la órden                              | `?comments=` permite agregar un comentario para la orden que será creada.                                                                                                                                                             |
| OrderController     | PUT         | /orders/{id}                                 | Permite actualizar los datos de una órden                                                                                                  ||
| OrderController     | PATCH       | /orders/{order_id}/status/{order_status}     | Permite modificar el status de una órden                                                                                                   | `?cancelled_by_client=` permite establecer si fue cancelada por el cliente                                                                                                                                                            |
| OrderController     | PATCH       | /orders/{order_id}                           | Permite calificar una órden                                                                                                                | -`?qualification=` calificaci[on asignada<br/> -`?qualification_message=` mensaje asociado a la calificación                                                                                                                          |
| OrderController     | GET         | /max_price                                   | Permite obtener la órden con mayor precio de una fecha dada                                                                                | -`?date=` la fecha seleccionada para obtener la órden                                                                                                                                                                                 |
| OrderController     | GET         | /{supplier_id}/max_supplier_products         | Permite obtener la órden con la cantidad máxima de productos del proveedor especificado                                                    ||
| OrderController     | POST        | /orders                                      | Permite crear una órden                                                                                                                    ||
| OrderController     | PATCH       | /{order_id}/{address_id}                     | Permite modificar la dirección de entrega de una órden, pasando el ID de la órden y el ID de la dirección de entrega.                      ||
| ProductController   | POST        | /products/{supplier_id}                      | Permite crear un producto para un proveedor especificando su ID                                                                            ||
| ProductController   | GET         | /products/{product_id}                       | Permite obtener un producto por ID                                                                                                         ||
| ProductController   | GET         | /products                                    | Permite listar todos los productos, o aquellos productos que filtren su ID o por ID del proveedor. Los parámetros son excluyentes.         | -`?supplier_id=` para filtrar por ID de proveedor <br/>-`?product_id=` para filtrar por id de producto                                                                                                                                |
| ProductController   | PUT         | /products/{product_id}                       | Permite actualizar los datos de un producto                                                                                                ||
| ProductController   | GET         | /products/average                            | Permite obtener un listado de productos por tipo, y su precio promedio                                                                     ||
| ProductController   | GET         | /products/{product_id}/prices_between_dates  | Permite obtener los precios históricos de un producto entre las fechas especificadas                                                       | -`?from_date=` fecha desde<br/>-`?todate=` fecha hasta                                                                                                                                                                                |
| SupplierController  | GET         | /suppliers/{supplier_id}                     | Obtiene un proveedor por su ID                                                                                                             ||
| SupplierController  | GET         | /suppliers                                   | Permite filtrar y obtener los proveedores que cumplan los criterios de búsqueda. Estos criterios no son excluyentes, pero pueden omitirse. | -`?supplier_type=` permite filtrar por tipo de proveedor<br/>-`?product_type=` permite filtrar por tipo de producto<br/>-`?qualification_over=` permite filtrar los proveedores que estén por encima de la calificación especificada. |
| SupplierController  | GET         | /suppliers/qualified                         | Permite obtener los proveedores que tengan al menos una estrella                                                                           ||
| SuppliersController | GET         | /suppliers/all_product_types                 | Permite obtener los proveedores que tienen productos de todos los tipos.                                                                   ||
| SuppliersController | GET         | /suppliers/ten_more_orders                   | Obtiene los proveedores con 10 o más órdenes                                                                                               ||
| SupplierController  | DELETE      | /suppliers/{supplier_id}/{product_id}        | Permite eliminar un producto de un proveedor especificando el ID de c/u                                                                    ||
| SupplierController  | POST        | /suppliers                                   | Permite crear un nuevo proveedor                                                                                                           ||

## Consideraciones generales en el diseño del modelo de datos

Se realizaron algunas modificaciones al modelo.
En el caso de la clase/entidad **DeliveryMan**, se anotó la propiedad *free* como Transient, para que no sea almacenada en la tabla correspondiente de la BD. 
```
    @Transient
    private boolean free = true;
```

En el caso de la propiedad *ordersPending*, se reemplazó por la propiedad *pendingOrder*, ya que no tenía sentido tener una lista con las órdenes pendientes, porque el repartidor solo puede repartir de a una órden.
Antes:
```
    private List<Order> ordersPending;
```
Después
```
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    private Order pendingOrder;
```

Ahora se determina si el repartidor está libre mediante la verificación de si existe o no una órden pendiente:
```
    public boolean isFree() {
        return pendingOrder == null;
    }
```

Los **Proxy Repositories** permiten acceder a la capa de persistencia e implementar los métodos de persistencia y recuperación de cada caso, sin que desde la capa de servicios se acceda directamente, entonces si se define la conexión a un nuevo motor de BD, solo se deberían modificar los repositorios, y en el caso de ser necesario (aunque la idea es mantener lo más agnóstica posible esta capa), aquellos métodos de la capa de ***Repository proxy***.
Para el caso del manejo de los estados de las órdenes, se implementa una clase command para cada tipo de estado, que realiza cosas distintas para cada caso.
Estas clases se definen en el path *src/main/java/com/bd/tpfinal/proxy/repositories/command*

### Modelo de datos JPA

Creé una clase abstracta PersistentEntity, de la cual heredan todas las clases de persistencia. Esta clase solo implementa el identificador de la entidad, que para el caso de JPA se establece como un autoincrementable de tipo Long.

```
@MappedSuperclass
public abstract class PersistentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
```

Las clases siguientes clases implementan un campo con la versión del objeto ***@Version***, que se utiliza para preservar la integridad del objeto de persistencia haciendo uso de bloqueo optimista (optimistic lock). Esto asegura que se mantenga la integridad cuando se realicen operaciones de actualización y para control optimista de concurrencia.
- User
- SupplierType
- Supplier
- Product
- Order
- HistoricalProductPrice
- Item

Las relaciones m:1 se mapean con estrategia ***EAGER***, es decir que el objeto relacionado se recupera cuando se recupera el objeto que lo contiene, por ejemplo:

```
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @NotNull
    private SupplierType type;
```

En el caso de las relaciones 1:m, la estrategia de recuperación es ***LAZY***, de modo que los objetos de la relación se recuperan de forma perezosa, para que tarde menos en recuperarse el objeto principal.

```
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<HistoricalProductPrice> prices = new ArrayList<>();
```

Las tablas **User** y **OrderStatus** siguen una estrategia de herencia de tipo SingleTable, es decir que las entidades se mapean en una misma tabla, y se utiliza un campo discriminador en las subclases para poder identificar a que tipo de objetos hace referencia cada fila.

En el caso de la clase abstracta **OrderStatus**:
```
@Entity
@Table(name = "order_status")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class OrderStatus extends PersistentEntity 
```
y sus subclases, por ejemplo:
```
@Entity
@DiscriminatorValue("PENDING")
public class Pending extends OrderStatus{
```

En el caso de **User**:
```
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class User extends PersistentEntity 
```
y sus subclases, por ejemplo:
```
@Entity
@DiscriminatorValue("DELIVERY_MAN")
public class DeliveryMan extends User
```

Existen tres relaciones m:m en el modelo, para las tablas:
- ProductType
- Supplier
- SupplierType

**ProductType**
```
    @ManyToMany(cascade= CascadeType.ALL)
    @JoinTable(name="products_products_types",
            joinColumns=@JoinColumn(name="product_id"),
            inverseJoinColumns=@JoinColumn(name="product_type_id"))
    private List<Product> products = new ArrayList<>();

```

**Supplier**
```
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name="supplier_products",
            joinColumns=@JoinColumn(name="supplier_id"),
            inverseJoinColumns=@JoinColumn(name="product_id"))
    private List<Product> products = new ArrayList<>();
```

**SupplierType**
```
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "supplier_types_supplier",
            joinColumns = { @JoinColumn(name = "supplier_id") },
            inverseJoinColumns = { @JoinColumn(name = "supplier_type_id") })
    private List<Supplier> suppliers = new ArrayList<>();
```

Para el caso de JPA, se creó una clase nueva **SupplierWithOrdersCount**, que contiene un campo transient ***ordersCount*** para llevar la cantidad de ordenes por proveedor. Me pareció que esta estrategia resultaba buena, ya que luego realicé un repositorio con un método para obtener la cantidad de órdenes por proveedor, con al menos 10 órdenes, como se indica a continuación:

```
    @Query(value = " select id, name, cuil, address, coords, qualificationOfUsers, typeId, type, count(*) counter from  ( " +
            "    select s.id, s.name, s.cuil, s.address, s.coords, s.qualification_of_users as qualificationOfUsers, " +
            "s.type_id as typeId, t.name as type,  count(o.id) AS count " +
            "from orders o " +
            "inner join items i on(o.id = i.order_id)  " +
            "inner join  products p on (i.product_id = p.id) " +
            "inner join suppliers s on(s.id=p.supplier_id) " +
            "inner join product_types t on(t.id=p.type_id) " +
            "group by o.id, s.id) tabla group by name ORDER BY counter DESC limit 10", nativeQuery = true)
    List<Map<String, Object>> suppliersAtLeast10Orders();
```

Los repositorios que se crearon son:
- ClientRepository
- DeliveryManRepository
- HistoricalProductPriceRepository
- ItemRepository
- OrderRepository
- ProductRepository
- ProductTypeRepository
- SupplierRepository
- SupplierTypeRepository
- SupplierWithOrdersCountRepository

Siempre que fue conveniente, se optó por utilizar métodos definidos con spring data, pero en los casos en los que resultaba más simple, se utilizaron queries nativas. 

### Modelo de datos MongoDB

Para el caso de MongoDB, el id en **PersistentEntity** se declara como un String y al crearse la entidad se establece un valor de la clase ObjectId
```
public abstract class PersistentEntity implements Serializable {

    @Id
    private String id = String.valueOf(ObjectId.get());//String.valueOf(UUID.randomUUID());

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
```
Los repositorios que se crearon son:
- ClientRepository
- DeliveryManRepository
- HistoricalProductPriceRepository
- ItemRepository
- OrderRepository
- ProductRepository
- ProductTypeRepository
- SupplierRepository
- SupplierTypeRepository


Del mismo modo que en JPA, las clases siguientes implementan un campo con la versión del objeto ***@Version***, que se utiliza para preservar la integridad del objeto de persistencia haciendo uso de bloqueo optimista (optimistic lock).
- User
- SupplierType
- Supplier
- Product
- Order
- HistoricalProductPrice
- Item

En varios casos, las relaciones equivalentes a las relaciones m:1 en JPA, mapean directamente al objeto, embebiendo en el documento el objeto relacionado. Por ejemplo en el caso del supplierType en la clase Supplier:

```    
  private SupplierType type;
```

Para el caso de las colecciones, se utiliza la anotación  **@DBRef** para relacionar los documentos asociados, también en este caso se recuperan de modo perezoso. 
Por ejemplo:

```
    @DBRef(lazy = true)
    private List<Product> products = new ArrayList<>();
```

Dado que algunos entidades relacionadas se mapean como objetos embebidos, no fue necesario anotar todas las clases de persistencia como @Document.

Para la persistencia de los objetos, tuve que cambiar la manera en la que se realizaba la persistencia de los objetos relacionados, ya que en JPA se persistía en cascada. Además para evitar problemas de actualización de los datos, tuve que implementar la lectura de los objetos. 
En el siguiente ejemplo, usando la clase **DataInitialization**, pueden verse que los cambios necesarios:

En JPA
```
    private void generateOrders(){
        if (clients.size() == 0)
            clients = clientRepository.findAll().stream().map(c -> c.getId()).collect(Collectors.toList());
        for (int i = 0; i < numberOfOrders; i++) {
            Order order = new Order();
            order.setNumber(random.nextInt(100000));
            order.setDateOfOrder(new Date());
            order.setTotalPrice(0f);
            Pending status = new Pending();
            status.setName();
            status.setOrder(order);
            order.setStatus(status);

            Client client = clientRepository.findById(clients.get(random.nextInt(clients.size()))).get();
            client.addOrder(order);

            order.setClient(client);
            if (client.getAddresses().size() > 0)
                order.setAddress(client.getAddress(0));
            orders.add(orderRepository.save(order).getId());
        }
    }
```
En MongoDB
```
    private void generateOrders(){
        if (clients.size() == 0)
            clients = clientRepository.findAll().stream().map(c -> c.getId()).collect(Collectors.toList());
        for (int i = 0; i < numberOfOrders; i++) {
            Order order = new Order();
            order.setNumber(random.nextInt(100000));
            order.setDateOfOrder(new Date());
            order.setTotalPrice(0f);
            Pending status = new Pending();
            status.setName();
            order.setStatus(status);

            Client client = clientRepository.findById(clients.get(random.nextInt(clients.size()))).get();

            order.setClient(client);
            if (client.getAddresses().size() > 0)
                order.setAddress(client.getAddress(0));
            order = orderRepository.save(order);
            clientRepository.save(client);
            client.addOrder(order);
            orders.add(order.getId());
        }
    }
```

Utilicé bastante los métodos de Spring Data, ya que me resultaron bastante útiles. Pude utilizar algunos de los que estaban definidos en los repositorios de JPA.
Los nombres de algunos de los métodos quedaron bastante largos, pero cumplen con su cometido, como es el ejemplo del método de **HistoricalProductPriceRepository**:
```
List<HistoricalProductPrice> findByStartDateGreaterThanAndFinishDateLessThanAndProduct_Id(Date fromDate, Date toDate, String id)
```

