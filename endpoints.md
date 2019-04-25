# Admin ONLY Routes

## User Controller - Protected
1. GET -    /users - find all users
2. GET -    /users/{userId} - get user by userId
3. POST -   /users/addadmin - add admin user
4. DELETE - /users/{userId} - delete a user by userId

## Order Controller
1. GET - /orders - get all orders
2. GET - /orders/{orderId} - get order by orderId
3. GET - /orders/unshipped - get all unshipped orders
4. GET - /orders/shipped - get all shipped orders
5. PUT - /orders/updatshippingstatus/{orderId}/{status} - update shipping status

## Supplier Controller
1. GET -    /suppliers - get all suppliers
2. GET -    /suppliers/{supplierId} - get supplier by id
3. POST -   /suppliers - add supplier
4. POST -   /suppliers/supplierId/{supplierId}/productId/{productId} - add supplier to product in supplierproduct join table
5. PUT -    /suppliers/{supplierId} - edit supplier
6. DELETE - /suppliers/{suppliereid} - delete supplier by supplierId
7. DELETE - /suppliers/supplierId/{supplierId}/productId/{productId} - delete supplier to product in supplierproduct join table

## Product Controller
1. GET -  /products - get all products
2. GET -  /products/{productId} - get product by productId
3. PUT -  /products/{productId} - edit product
4. POST - /products - save new product
5. DELETE /products/{producteid} - delete product by productId




# User OR Admin Routes

## Cart Controller
1. GET -    /cart/{userId} - get users cart by id
2. POST -   /cart/addtocart/{userId}/{productId}/{inventory} - add to cart, if exists add to inventory
3. PUT -    /cart/modifyquantityincart/{userId}/{productId}/{inventory} - modify inventory in cart
4. DELETE - /cart/remove/{userId}/{productId} - delete item from cart
5. DELETE - /cart/modifytozero/{userId}/{productId} - modify inventory of item in cart to 0
6. DELETE - /cart/deleteall/{userId} - delete all items from cart
7. POST -   /cart/buy - buy items in cart

## Customer Controller
1. GET - /customer/userId/{userId} - get 1 user by userId
2. GET - /customer/username/{username} - get 1 user by username ** change to /cart/username/{username}
3. PUT - /customer/update - update customer

4. GET - /customer/orders/orderId/{orderId} - get order by orderId
5. GET - /customer/orders/userId/{userId} - get all orderitems from userId



# Unprotected Routes

## Signup Controller
1. GET - /signup - signup new users

## Shop Controller
1. GET - /shop - get all items without sensitive relational data
2. GET - /shop/{page} - get 10 items of page number without sensitive relational data

3. GET - /shop/naturalsearch/{search string}/page/{page} - get 10 items of page number of item's with name like itemname using MySQL's algorithm
4. GET - /shop/criteria/{search string}/page/{page} - get 10 items of page number of item's with name like itemname using criteria builder to have multiple wildcard LIKE

5. GET - /shop/reviews - get all reviews
6. GET - /shop/reviewitems - get all reviewitems
7. GET - /shop/reviewitems/{page} - get 10 reviewitems of page number