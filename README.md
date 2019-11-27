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