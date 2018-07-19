provider "azurerm" { }

resource "azurerm_resource_group" "fiftyshadesofserverless-rg" {
  name     = "50shadesofserverless-rg"
  location = "northeurope"
}

resource "azurerm_storage_account" "fiftyshadesofserverless" {
  name                     = "50shadesofserverless"
  resource_group_name      = "${azurerm_resource_group.fiftyshadesofserverless-rg.name}"
  location                 = "${azurerm_resource_group.fiftyshadesofserverless-rg.location}"
  account_tier             = "Standard"
  account_replication_type = "LRS"
}

resource "azurerm_app_service_plan" "fiftyshadesofserverless" {
  name                = "50shadesofserverless-plan"
  location            = "${azurerm_resource_group.fiftyshadesofserverless-rg.location}"
  resource_group_name = "${azurerm_resource_group.fiftyshadesofserverless-rg.name}"
  kind                = "FunctionApp"

  sku {
    tier = "Dynamic"
    size = "Y1" # Consumption plan for Function App
  }
}

resource "azurerm_function_app" "50shadesofserverless-app" {
  name                      = "50shadesofserverless"
  location                  = "${azurerm_resource_group.fiftyshadesofserverless-rg.location}"
  resource_group_name       = "${azurerm_resource_group.fiftyshadesofserverless-rg.name}"
  app_service_plan_id       = "${azurerm_app_service_plan.fiftyshadesofserverless.id}"
  storage_connection_string = "${azurerm_storage_account.fiftyshadesofserverless.primary_connection_string}"
}