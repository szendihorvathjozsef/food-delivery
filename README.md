# food-delivery
Food delivery application

**Adattípusok**

* AddressType: BILLING | TRANSPORT
* OrderStatus: ORDERED | IN_PROGRESS | FINISHED
* UserStatus: ACTIVE | NEED_ACTIVATION | BANNED
* Couponstatus: USED, UNUSED
* Authorities = szerepkörök, jelenlegiek
    * USER
    * ADMINISTATOR
* UserAddress : cím
    * id: number
    * address: maga cím, várossal
    * postCode: irányítószám
    * type: AddressType
* User
  * id: number
  * login: string
  * firstName: string
  * lastName: string
  * email: string
  * status: UserStatus
  * authorities: string[]
  * addresses: UserAddress[]
  * createdBy: string
  * createdDate: timestamp
  * lastModifiedBy: string
  * lastModifiedDate: timestamp
* Item
  * id: number
  * name: string
  * price: float
  * kCal: number
  * protein: number
  * fat: number
  * carbs: number
  * imageName: string
  * itemType: string
  * allergens: string[]
* OrderItem
  * id: number
  * quantity: number
  * order: Order //ignored
  * item: Item
* Order
  * id: number
  * totalCost: double
  * status: OrderStatus
  * startTime: DateTime
  * endTime: DateTime
  * createdOn: timestamp
  * updatedOn: timestamp
  * orders: OrderItems[]
  * user: User
* CouponType
    * id: number
    * name: string
    * percent: number
* Coupon
  * id: number
  * status: CouponStatus
  * type: CouponType
  * user: User

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
  "email": "valami@valami.org",
  "phoneNumber": "+36301234567",
  "addresses": []
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
login: felhasználónév vagy email cím

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
* POST /orders :   
rendelés felvétele regisztrált felhasználónál  
kuponoknál a használt kuponokat kell beküldeni
```json
{
  "coupons": [],
  "order": {
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
}
```

* POST /orders :   
rendelés felvétele nem regisztrált felhasználónál  
kuponoknál a használt kuponokat kell beküldeni
```json
{
  "order": {
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
        "login": "asd",
        "password": "valami",
        "firstName": "József",
        "lastName": "Szendi-Horvtáh",
        "email": "1alien971@gmail.com",
        "phoneNumber": "+36301234567",
        "addresses": [
            {
                "postCode": 8100,
                "address": "Várpalota Magyari utca 4.",
                "type": "TRANSPORT"
            },
            {
                "postCode": 8100,
                "address": "Várpalota Magyari utca 4.",
                "type": "BILLING"
            }
        ] 
    }
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
Order id megadása egy tömben
```json
[1, 2, 3]
```
* DELETE /orders/{orderId} : rendelés törlése  
Item id megadása egy tömben  

* GET /orders/user :   
    * jelenleg bejelentkezett felhasználónak visszadja az összes rendelését
* GET /orders/user/in-progress :  
    * jelenleg bejelentkezett felhasználónak visszadja az összes folyamatban lévő rendelését
    
* GET /orders/in-progress : kilistázza az összes folyamatban lévő rendelést

* POST /orders/finished  
A rendelések id-jei
```json
{
  "ids": [1, 2, 3]
}
```

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

* POST /coupons : kupon létrehozása
    * Alapból UNUSED ként jön létre
```json
{
    "type": {
        "id": 1
    },
    "user": {
        "id": 1
    }
}
```

* DELETE /coupons/{couponId} : kupon törlése

* GET /coupons/unused : bejelentkezett felhasználó fel nem használt kuponjai
* GET /coupons/used: bejelentkezett felhasználó felhasznált kuponjai 

**Kupon típus kezelése**

* GET /coupon-types : kuponok listázása
* POST /coupon-types : kupon létrehozása
```json
{
	"name": "test2",
	"percent": 100
}
```
* DELETE /coupon-types/{couponId} : kupon törlése

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
