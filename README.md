# food-delivery
Food delivery application

**Adattípusok**

* AddressType: BILLING | TRANSPORT
* OrderStatus: ORDERED | IN_PROGRESS | FINISHED
* UserStatus: ACTIVE | NEED_ACTIVATION | BANNED
* Authorities = szerepkörök, jelenlegiek
    * USER
    * ADMINISTATOR
* UserAddress : cím
    * address: maga cím, várossal
    * postCode: irányítószám
    * type: AddressType
* User
  * Id: number
  * Login: string
  * FirstName: string
  * LastName: string
  * Email: string
  * Status: UserStatus
  * Authorities: string[]
  * Addresses: UserAddress[]
  * CreatedBy: string
  * CreatedDate: timestamp
  * LastModifiedBy: string
  * LastModifiedDate: timestamp
* Item
  * Id: number
  * Name: string
  * Price: float
  * Price: float
  * KCal: number
  * Protein: number
  * Fat: number
  * Carbs: number
  * ImageName: string
  * ItemType: string
  * Allergens: string[]
* OrderItem
  * Id: number
  * Quantity: number
  * Order: Order //ignored
  * Item: Item
* Order
  * Id: number
  * TotalCost: double
  * Status: OrderStatus
  * StartTime: DateTime
  * EndTime: DateTime
  * CreatedOn: timestamp
  * UpdatedOn: timestamp
  * Orders: OrderItems[]
  * User: User
* Coupon
  * Id: number
  * Name: string
  * Percent: number
  * ItemType: string
  * User: User

A következő végpontok léteznek:  

**Fiók műveletek**

* GET /authentication: Bejelentkezés
```json
{
  "username": "admin",
  "password": "valami",
  "rememberMe": false
}
```

* POST /register: Regisztráció
```json
{
  "login": "",
  "password": "",
  "firstName": "",
  "lastName": "",
  "email": "",
  "addresses": []
}
```

* GET /authenticate: Be vagy-e jelentkezve
* GET /account: visszaadja a fiókot
* POST /account: fiók adatok módosítása  
Ezt várja a rendszer.
```json
{
  "firstName": "valami",
  "lastName": "valami2",
  "email": "valami@valami.org"
}
```
* POST /account/change-password: jelszóváltoztatás  
Ezt várja a rendszer.
```json
{
    "currentPassword": "",
    "newPassword": ""
}
```
* POST /account/reset-password/init: jelszó reset init
Ezt várja a rendszer.
```json
{
  "email": ""
}
```
* POST /account/reset-password/finish: jelszó reset finish  
Ezt várja a rendszer.
```json
{
  "key": "",
  "newPassword": ""
}
```

**Felhasználó műveletek**

* GET /users: felhasználók listázása lapozhatósággal
```
    /users?page={1}&size={2}&sort={3}
    page={1} : hányadik oldal, 0-ról indul, szám
    size={2} : hány adat jöjjön vissza, szám
    sort={3} : mi alapján rendezzen, string, pl sort=login,asc
   
    Példa: /users?page=0&size=20&sort=login,asc
```

* GET /users/{login}: login alapján lekérdezni egy felhasználót  
login: felhasználónév

* POST /users: felhasználó készítés  
Ezt várja a rendszer.
```json
{
  "login": "",
  "firstName": "",
  "lastName": "",
  "email": "",
  "status": "", 
  "authorities": []
}
```

* PUT /users: felhasználó adatainek frissítése  
Ezt várja a rendszer.
```json
{
  "login": "",
  "firstName": "",
  "lastName": "",
  "email": "",
  "status": "", 
  "authorities": []
}
```

* GET /authorities: szerekörök lekérdezése

**Item hozzáadás**

* GET /items : itemek kilistázása
* GET /items/{itemId} : egy item lekérése
* POST /items : item létrehozása
```json
{
  "name": "Test item2",
	"price": 200,
	"kCal": 4,
	"protein": 4,
	"fat": 5,
	"carbs": 6,
	"itemType": "Leves",
	"allergens": [
		"tejfehérje"
	]
}
```
* PUT /items : item módosítása
```json
{
  "id": 1,
  "name": "Test item2",
  "price": 200,
  "kCal": 4,
  "protein": 4,
  "fat": 5,
  "carbs": 6,
  "itemType": "Leves",
  "allergens": [
    "tejfehérje"
  ]
}
```
* DELETE /items/{id} : item törlése

* POST /items/images : item-hez kép feltöltése
```
  Muszáj, hogy form-data-ként kell küldeni az adatokat
  file : maga file
  itemName: az item neve
```
* GET /items/images/{filename} : item-hez kép megnézése

**Rendelés kezelés**

* GET /orders : rendelés kilistázás

* GET /orders/{orderId} : egy rendelés lekérése
* POST /orders : rendelés felvétele
```json
{
  "totalCost": 10000,
  "startTime": "2019-11-27 12:58",
  "endTime": "2019-11-27 13:58",
  "orders": [
    {
      "quantity": 5,
      "item": {
        "id": 1
      }
    }
  ],
  "user": {
    "id": 1
  }
}
```
* PUT /orders : rendelés módosítása
```json
{
  "id": 1,
  "totalCost": 10000,
  "startTime": "2019-11-27 12:58",
  "endTime": "2019-11-27 13:58",
  "orders": [
    {
      "quantity": 5,
      "item": {
        "id": 1
      }
    }
  ],
  "user": {
    "id": 1
  }
}
```
* POST /orders/finish : rendelések befejezése  
Item id megadása egy tömben
```json
[1, 2, 3]
```
* DELETE /orders/{orderId} : rendelés törlése  
Item id megadása egy tömben

**Allergének kezelése**

* GET /item-allergens : allergének listázása  

* POST /item-allergens : allergén hozzáadása
```json
{
  "allergen": "mogyoró"
}
```
* POST /item-allergens/more : több allergén hozzáadása
```json
{
  "allergens": ["mogyoró"]
}
```
* DELETE /item-allergens/{allergenName} : allergén törlése

* DELETE /item-allergens/more : több allergén törlése
```json
{
  "allergens": ["mogyoró"]
}
```

**Kupon kezelése**

* GET /coupons : kuponok listázása

* POST /coupons : kupon hozzáadása
```json
{
  "name": "",
  "percent": 0,
  "itemType": "",
}
```

* PUT /coupons : kupon módosítása
```json
{
  "id": 1,
  "name": "",
  "percent": 0,
  "itemType": "",
}
```

* DELETE /coupons/{couponId} : kupon törlése


**Statisztika**

* GET /statistics/day/{date} : egy napra visszaadja miből mennyi fogyott  
Visszatérés
```json
{
  "dailyStatistics": [
    {
      "name": "Bolognai",
      "quantity": 10 
    }
  ]
}
```
* GET /statistics/between/{startDate}/{endDate} : egy időszakra visszaadja, napokra bontva mennyi volt a bevétel  
Visszatérés
```json
{
  "between": [
    {
      "day": "2019-11-26",
      "income": 50000
    },
    {
      "day": "2019-11-27",
      "income": 40000
    }
  ]
}
```