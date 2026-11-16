# E-Commerce Shopping Cart

## Project Overview

This project is a simple E-Commerce Shopping Cart system implemented in Java.

The main goal of the project is to demonstrate how different design patterns can work together inside one application.

The system allows us to:

- create different types of products;
- add extra features such as gift wrapping and warranty;
- calculate shipping depending on the product and delivery type;
- generate different product codes;
- apply different discount strategies;
- create an order step by step;
- process payment through a legacy bank API;
- notify the customer and warehouse when the order status changes;
- coordinate the checkout process through one Facade.

The project uses 8 design patterns:

1. Factory Method
2. Observer
3. Builder
4. Visitor
5. Decorator
6. Facade
7. Strategy
8. Adapter

---

## Project Structure

```text
src/main/java
└── shop
    │
    ├── adapter
    │   ├── PaymentGateway.java
    │   ├── LegacyBankAPI.java
    │   └── LegacyBankAdapter.java
    │
    ├── decorator
    │   ├── ProductDecorator.java
    │   ├── GiftWrapDecorator.java
    │   └── WarrantyDecorator.java
    │
    ├── facade
    │   └── CheckoutFacade.java
    │
    ├── factory
    │   ├── ProductCreator.java
    │   ├── PhysicalProductCreator.java
    │   ├── DigitalProductCreator.java
    │   └── GiftCardProductCreator.java
    │
    ├── model
    │   ├── Product.java
    │   ├── PhysicalProduct.java
    │   ├── DigitalProduct.java
    │   ├── GiftCardProduct.java
    │   └── Customer.java
    │
    ├── observer
    │   ├── OrderObserver.java
    │   ├── CustomerNotifier.java
    │   └── WarehouseNotifier.java
    │
    ├── order
    │   ├── Order.java
    │   ├── OrderBuilder.java
    │   ├── DeliveryType.java
    │   └── OrderStatus.java
    │
    ├── strategy
    │   ├── DiscountStrategy.java
    │   ├── DiscountCalculator.java
    │   ├── NoDiscount.java
    │   ├── StudentDiscount.java
    │   └── VipDiscount.java
    │
    ├── visitor
    │   ├── ProductVisitor.java
    │   ├── ShippingVisitor.java
    │   ├── ProductCodeVisitor.java
    │   └── ProductTypeCountVisitor.java
    │
    └── Demo.java
```

---

## Design Patterns

### 1. Factory Method

Factory Method is used to create different product types.

The system has three concrete products:

- `PhysicalProduct`
- `DigitalProduct`
- `GiftCardProduct`

`ProductCreator` defines the common creation method, while concrete creators create the required product.

Example:

```java
ProductCreator creator = new PhysicalProductCreator();
Product laptop = creator.createProduct("ASUS TUF Gaming Laptop", 520000);
```

This keeps product creation separate from the code that uses the products.

---

### 2. Observer

Observer is used for order status notifications.

`Order` acts as the subject and stores registered observers.

The concrete observers are:

- `CustomerNotifier`
- `WarehouseNotifier`

When the order status changes, all registered observers are notified.

For example:

```text
PAID

Customer: payment accepted
Warehouse: prepare order
```

For `SHIPPED` and `DELIVERED`, the two observers also react differently.

---

### 3. Builder

Builder is used to create an `Order` step by step.

An order contains several values such as:

- customer;
- products;
- address;
- delivery type;
- shipping cost;
- discount.

Instead of using one long constructor, the order can be created using:

```java
Order order = new OrderBuilder()
    .setCustomer(customer)
    .addProduct(product)
    .setAddress("Astana")
    .setDeliveryType(DeliveryType.STANDARD)
    .setShippingCost(2500)
    .setDiscount(10000)
    .build();
```

The Builder also validates the input, calculates the subtotal and calculates the final total.

The final total is calculated as:

```text
subtotal - discount + shipping cost
```

---

### 4. Visitor

Visitor is used for operations that depend on the concrete product type.

All products implement:

```java
accept(ProductVisitor visitor)
```

and call:

```java
visitor.visit(this);
```

The project contains several visitors.

#### ShippingVisitor

Calculates shipping differently depending on the product type and selected delivery type.

Example for STANDARD delivery:

```text
Physical product → 2000 KZT
Digital product → 0 KZT
Gift card → 500 KZT
```

Example for EXPRESS delivery:

```text
Physical product → 3500 KZT
Digital product → 0 KZT
Gift card → 1000 KZT
```

#### ProductCodeVisitor

Generates different codes depending on the product type.

Example:

```text
Physical product → 37955942
Digital product → EAI7GGUC
Gift card → GIFT-27047
```

#### ProductTypeCountVisitor

Counts how many physical, digital and gift card products are present.

Visitor allows us to add new product-related operations without putting all operation logic directly inside the product classes.

---

### 5. Decorator

Decorator is used to dynamically add optional features to a product.

The project contains:

- `GiftWrapDecorator`
- `WarrantyDecorator`

Example:

```java
Product laptop = new PhysicalProduct("Laptop", 520000);

laptop = new GiftWrapDecorator(laptop);
laptop = new WarrantyDecorator(laptop);
```

The final product can contain both extra features without creating separate classes such as:

```text
LaptopWithGiftWrap
LaptopWithWarranty
LaptopWithGiftWrapAndWarranty
```

---

### 6. Strategy

Strategy is used for different discount algorithms.

The project contains:

- `NoDiscount`
- `StudentDiscount`
- `VipDiscount`

`DiscountCalculator` is the Context and stores the currently selected strategy.

Example:

```java
DiscountCalculator calculator =
    new DiscountCalculator(new StudentDiscount());
```

The strategy can also be changed at runtime.

The current discounts are:

```text
NoDiscount      → 0%
StudentDiscount → 10%
VipDiscount     → 20%
```

---

### 7. Adapter

Adapter is used to connect the shopping system with an incompatible legacy bank API.

Our application expects:

```java
PaymentGateway.pay(orderId, amount)
```

but the legacy bank uses:

```java
makeTransaction(amountInMinorUnits, currency, reference)
```

`LegacyBankAdapter` converts the call from the interface used by our system to the format expected by `LegacyBankAPI`.

This allows the checkout system to work with the old bank API without changing the bank class.

---

### 8. Facade

`CheckoutFacade` provides one simplified entry point for the checkout process.

The client does not need to manually coordinate all other classes.

The Facade performs the main checkout workflow:

```text
Products
   ↓
ShippingVisitor
   ↓
Discount Strategy
   ↓
OrderBuilder
   ↓
Order
   ↓
PaymentGateway
   ↓
LegacyBankAdapter
   ↓
Payment
   ↓
Order status = PAID
   ↓
Observer notifications
   ↓
Product codes
```

The client only needs to call:

```java
checkoutFacade.placeOrder(...);
```

This keeps the `Demo` class simple and moves the checkout coordination into one place.

---

## Application Flow

The general application flow is:

```text
Factory Method
      ↓
Create products
      ↓
Decorator
      ↓
Add optional product features
      ↓
CheckoutFacade
      ↓
Visitor calculates shipping
      ↓
Strategy applies discount
      ↓
Builder creates Order
      ↓
Adapter processes payment
      ↓
Observer sends notifications
      ↓
Order is completed
```

After checkout, the order status can continue changing:

```text
CREATED
↓
PAID
↓
SHIPPED
↓
DELIVERED
```

Every status change notifies the registered observers.

---

## Team Pattern Distribution

| Team Member | Patterns |
|---|---|
| Anel | Factory Method, Observer |
| Yerkezhan | Builder, Visitor |
| Albina | Decorator, Facade |
| Aziza | Strategy, Adapter |

Each team member is responsible for two design patterns, while all patterns are integrated into the same project.

---



## How to Run

1. Open the project in IntelliJ IDEA.
2. Make sure Java 17 is selected as the project SDK.
3. Open:

```text
src/main/java/shop/Demo.java
```

4. Run the `main()` method.

The console will display:

- created products;
- decorated product information;
- shipping calculation;
- payment processing;
- customer and warehouse notifications;
- generated product codes;
- final order information.

---

## Example Scenario

The demo creates:

```text
ASUS TUF Gaming Laptop
Java Programming E-Book
Gift Card
```

Gift Wrap and a one-year Warranty are added to the laptop.

A Student Discount is selected.

The checkout system then:

1. calculates shipping;
2. calculates the discount;
3. builds the order;
4. processes the payment;
5. changes the order status to `PAID`;
6. notifies the customer and warehouse;
7. generates product codes.

The order is later changed to:

```text
SHIPPED
DELIVERED
```

and the observers are notified again.

---

## Notes

For simplicity, most prices in the project are stored as `double`, while the Strategy and payment parts use `BigDecimal`.

Since this is an educational project and prices are mainly whole KZT values, this is acceptable for the current implementation. In a real e-commerce system, monetary values should normally use `BigDecimal` consistently to avoid floating-point precision problems.

---

## Conclusion

This project demonstrates how several design patterns can work together inside one e-commerce system.

Each pattern has a separate responsibility, while the `CheckoutFacade` connects the main parts of the checkout process.

The project demonstrates both the individual purpose of each pattern and their integration inside one application.
