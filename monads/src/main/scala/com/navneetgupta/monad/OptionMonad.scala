package com.navneetgupta.monad

object OptionMonad {
  trait Manager {
    val subOrdinates: Option[Manager]
  }
  object ManagerService {
    def loadUser(name: String): Option[Manager] = ???
  }
  val getSubOrdinates = (manager: Manager) => manager.subOrdinates

  // Now if we donot know the concept of Monad .. or if Option does not behave as Monad.
  // To get the subOrdinates, or  GrandSubOrdinates of a Manager we need to Load Manager first then check
  // if it is defined if defined then get subOrdinates then again checked if it is defined and then check for GrandSubOrdinates similarly.

  // So To Get GrandChild:
  val grandSubOrdinates = ManagerService.loadUser("alice") match {
    case Some(u) =>
      u.subOrdinates match {
        case Some(c) =>
          c.subOrdinates
        case _ => None
      }
    case _ => None
  }

  // But if we treat Option as a Monad , And we know every Monad has a flatMap Fcuntion which is composable we can write

  val grandSubOrdinates2 = ManagerService.loadUser("alice").flatMap(getSubOrdinates).flatMap(getSubOrdinates)

  // we could also define lambda function instead of writting seperate function getChild like below
  val _grandSubOrdinates2 = ManagerService.loadUser("alice").flatMap(manager => manager.subOrdinates).flatMap(manager => manager.subOrdinates)

  // or in short Form
  val __grandSubOrdinates2 = ManagerService.loadUser("alice").flatMap(_.subOrdinates).flatMap(_.subOrdinates)

}
