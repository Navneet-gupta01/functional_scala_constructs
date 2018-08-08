package com.navneetgupta.monad

import scala.util.Success

object FutureMonad {
  // Main ways to consume a Future in scala is :
  //   1. future.onComplete to define a callback which will work with result of future
  //   2. future.flatMap to simply say operation to be done once future returns/completes

  //Here we will Do as Example

  //For each customer we must now get his/her order, check which item the order is for, get the corresponding
  // item from the database, make the actual purchase and write the result of purchase operation to log.

  // So here we can easily identify we need to use future and process on the response as below
  // customerId => Future(Customer) => Future(Orders) => Get Actual Items(itemIds) => Future(Items) => Write to log

  import scala.concurrent.Future
  import scala.concurrent.ExecutionContext.Implicits.global

  trait Customer
  trait Order
  trait Item
  trait PurchaseResult
  trait LogResult

  object CustomerService {
    def loadCustomer(usename: String): Future[Customer] = ???
  }

  object OrderService {
    def loadOrder(customer: Customer): Future[Order] = ???
  }

  object ItemService {
    def loadItem(order: Order): Future[Item] = ???
  }

  object PurchasingService {
    def purchaseItem(item: Item): Future[PurchaseResult] = ???
    def logPurchase(purchaseResult: PurchaseResult): Future[LogResult] = ???
  }

  //flatMap functions
  val loadOrder: Customer => Future[Order] = {
    customer => OrderService.loadOrder(customer)
  }
  val loadItem: Order => Future[Item] = {
    order => ItemService.loadItem(order)
  }
  val purchaseItem: Item => Future[PurchaseResult] = {
    item => PurchasingService.purchaseItem(item)
  }

  val logPurchase: PurchaseResult => Future[LogResult] = {
    purchaseResult => PurchasingService.logPurchase(purchaseResult)
  }

  val response =
    CustomerService.loadCustomer("username")
      .flatMap(loadOrder)
      .flatMap(loadItem)
      .flatMap(purchaseItem)
      .flatMap(logPurchase)

  // Examples if we do it like in optiona monad checking isDefined or not then using it will cause a callback hell.

}
