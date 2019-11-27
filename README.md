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
```json
{
  "firstName": "valami",
  "lastName": "valami2",
  "email": "valami@valami.org"
}
```
* POST /account/change-password: jelszóváltoztatás
```json
{
    "currentPassword": "",
    "newPassword": ""
}
```
* POST /account/reset-password/init: jelszó reset init
```json
{
  "email": ""
}
```
* POST /account/reset-password/finish: jelszó reset finish
```json
{
  "key": "",
  "newPassword": ""
}
```