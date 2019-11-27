# food-delivery
Food delivery application


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