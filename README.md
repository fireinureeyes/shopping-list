# Firebase-synced shopping list app for Android
Lightweight shopping list with a local database synchronized between devices over Firebase based on https://github.com/mauriciotogneri/shoppinglist

<p float="left">
<img src="https://raw.githubusercontent.com/fireinureeyes/shopping-list/main/app-screenshot-1.png" width="200">
<img src="https://raw.githubusercontent.com/fireinureeyes/shopping-list/main/app-screenshot-2.png" width="200">
<img src="https://raw.githubusercontent.com/fireinureeyes/shopping-list/main/app-screenshot-3.png" width="200">
</p>

## Features:
- Add shopping list item by searching
- Add custom note to any item in the cart
- Add general note (e.g. for items not in the app) also synced online
- Adding items to buy and removing items from the cart
- Showing the count of the items currently in cart

## How to get started after pulling this repository:
1. Create a new Firebase project https://firebase.google.com/
2. Within the project, create a realtime database and import the [db-export.json](https://github.com/fireinureeyes/shopping-list/blob/main/db-export.json) which has the following structure:
'items' root node with 203 key-value pairs, where key is the item ID and value expresses whether the item is currently in the shopping cart (0=no, 1=yes, anything else=yes with a note)

![database](https://raw.githubusercontent.com/fireinureeyes/shopping-list/main/database.png)

3. Download google-services.json of from the Firebase project setttings
4. Place the google-services.json file directly to the 'app' folder
5. Get the database URL from the file and use it to replace 'database_url' string in shoppinglist-master\app\src\main\res\values\strings.xml


