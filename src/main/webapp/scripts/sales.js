sessionStorage.setItem("lastPage", window.location.href);
$( document ).ready(function() {
    checkSession()
});

// Para revisar si ya se ha iniciado sesión
function checkSession(){
    //console.log(sessionStorage.getItem("session"))
    if (sessionStorage.getItem("session") == null || sessionStorage.getItem("session") == "none"){
        alert("Primero debes iniciar sesión")
        window.location.href = "index.html";
    }
}

const VAT = 0.19;
let customerData;
let productsData = [];

let saleTotal;
let saleVAT;
let saleValue;
let saleId;
let saleDetailId;
let userIdCard = sessionStorage.getItem("session");

let sale = new Object;
let saleDetail = new Object;

$.when(getSaleId().done(function(){
  saleIdLabel = document.getElementById("sale-id");
  saleIdLabel.innerHTML = saleId;
}));

addRow();
//SELECT max(nitproveedor) FROM  grupo16_equipo11.proveedores;
//Obtiene al cliente actual
function getSaleId(){
  //const urlLink = "http://localhost:8080/sales/lastId"
  const urlLink = "http://3.80.62.114:8080/tiendaVG-0.0.1-SNAPSHOT/sales/lastId"   

  return $.ajax({
    type: "GET",
    url: urlLink,

    error: function(){
      console.log("Error en la petición obteniendo el consecutivo");
    }
  }).done(function(data){
    saleId = data + 1

  });
  
}

function getSaleDetailId(){
  
  //const urlLink = "http://localhost:8080/saleDetails/lastId"  
  const urlLink = "http://3.80.62.114:8080/tiendaVG-0.0.1-SNAPSHOT/saleDetails/lastId"  
 
  return $.ajax({
    type: "GET",
    url: urlLink,

    error: function(){
      console.log("Error en la petición obteniendo el consecutivo");
    }
  }).done(function(data){

    saleDetailId = data + 1;
    
  });
  
}


function searchCustomer(customerId){
  //const urlLink = "http://localhost:8080/customers/"  
  const urlLink = "http://3.80.62.114:8080/tiendaVG-0.0.1-SNAPSHOT/customers/" 
  return $.ajax({
    type: "GET",
    url: urlLink + customerId,

    error: function(){
      console.log("Error en la petición");
    }
  }).done(function(data){
    if (data.customerIdCard == undefined){
      alert("El cliente no existe")
    }
    else{
      customerData = data;
    }
  });
  
}

function searchProduct(productId, localProductIndex){
  //const urlLink = "http://localhost:8080/products/"  
  const urlLink = "http://3.80.62.114:8080/tiendaVG-0.0.1-SNAPSHOT/products/"  
  return $.ajax({
    type: "GET",
    url: urlLink + productId,

    error: function(){
      console.log("Error en la petición");
    }
  }).done(function(data){
    if (data.productId == undefined){
      alert("El producto no existe")
    }
    else{
      data.quantity = 0;
      data.totalPrice = 0;
      productsData.splice(localProductIndex, 1, data);
      //console.log(productsData);
    }
  });

}

// Obtiene la identificación del cliente 
$('.sale-header').on('click', '#get-client-button', function(){
    
    clientId = document.getElementById("input1").value;
    clientNameLabel = document.getElementById("client-name");
    saleIdLabel = document.getElementById("sale-id");
    
    $.when(searchCustomer(clientId).done(function(){
      
      clientNameLabel.innerHTML = customerData.customerName;
      saleIdLabel.innerHTML = saleId;
    }))

})
// Crea la venta y la envía a la base de datos
$('.sale-footer').on('click', '.comfirm-button', function(){
  if (customerData) {
    if (productsData.length > 0){

      $.when(getSaleId().done(function(){

        saleIdLabel = document.getElementById("sale-id");
        saleIdLabel.innerHTML = saleId;
        getSaleData();

        $.when(createSale().then(function(results){
          createSaleDetails();
        }))

      }));

      }
    else {
      alert("Primero debes agregar un producto")
    }
  }
  else {
    alert("Primero debes ingresar un cliente")
  }
})
// Para el input de cantidad
$('.table').on('change', '.quantity-input', function() {
  getTotalAndQuantity(this);
})

// Obtiene la cantidad y actuliza el HTML así como el objeto en la lista
function getTotalAndQuantity(thisElement){
  const quantityInput = thisElement.closest(".quantity-input");
  const parent = thisElement.closest("tr");
  let produtTotalValueField = parent.querySelector('.product-total');
  const trIndex = $(thisElement).closest("tr").index();

  if(parent.querySelector("input").value != "" && quantityInput && quantityInput.value >= 0){
    let produtTotalPrice = productsData[trIndex].salePrice * quantityInput.value;
    productsData[trIndex].quantity = +quantityInput.value;
    productsData[trIndex].totalPrice = produtTotalPrice;
    
    produtTotalValueField.innerHTML = formatter.format(productsData[trIndex].totalPrice);
    getTotalValues();
  }

  if (quantityInput.value && quantityInput.value > 0 && $(thisElement.closest("tr")).is("tr:last-of-type") && thisElement.closest("tr").querySelector("input").value != "") {
    addRow()
    
  }
}

// Botones indiviudales por fila de producto
$('.table').on('click', '#get-product-button', function() {
   addProductRow(this);
})
$('.table').on('change', '.product-code-input', function() {
   addProductRow(this);
})
// añade el nombre del producto
function addProductRow(thisRow){
  const trIndex = $(thisRow).closest("tr").index();
  const parent = thisRow.closest("tr");
  let productInput = parent.querySelector(".product-code-input").value;
  let productNameField = parent.querySelector(".product-name");
  let quantityInput = parent.querySelector(".quantity-input");
  
  $.when(searchProduct(productInput, trIndex).done(function(){
    productNameField.innerHTML = productsData[trIndex].productName;
      getTotalAndQuantity(quantityInput);
  }))
}

// Suma el total de la columna (YA NO SE USA POR INSEGURIDAD)
function getColumnSum(tableId, columnClass) {
    let matchingElements = [...document.querySelector(`#${tableId}`).querySelectorAll(`.${columnClass}`)];
    matchingElements = matchingElements.map((elem) => +elem.textContent);
    return matchingElements ? matchingElements.reduce((a, b) => a + b) : 0;
}

function getTotalValues() {
  const saleTotalField = document.getElementById("sale-total")
  const saleVATField = document.getElementById("sale-total-VAT")
  const saleTotalValueField = document.getElementById("sale-total-and-VAT")
  saleTotal = sumProductsValue();
  saleVAT = saleTotal * VAT;
  saleValue = saleTotal + saleVAT;

  saleTotalField.innerHTML = formatter.format(saleTotal);
  saleVATField.innerHTML = formatter.format(saleVAT);
  saleTotalValueField.innerHTML = formatter.format(saleValue);
}

function sumProductsValue(){
  let sum = 0;
  for (product of productsData){
    sum += product.totalPrice;
  }
  return sum;
}

// función para crear la venta completa.
function getSaleData(){
  getSaleId();
  sale.customerIdCard = customerData.customerIdCard;
  sale.saleId = saleId;
  sale.saleTotal = saleTotal;
  sale.saleVAT = saleVAT;
  sale.saleValue = saleValue;
  sale.userIdCard = userIdCard;

}

function getSaleDetail(product){
  
  saleDetail.productId = product.productId;
  saleDetail.productQuantity = product.quantity;
  saleDetail.saleDetailId = saleDetailId;
  saleDetail.saleId = saleId;
  saleDetail.saleValue = product.salePrice;
  saleDetail.totalValue = product.totalPrice;
  saleDetail.vatvalue = product.totalPrice * VAT;

}
//Enviar la venta a la base de datos 
function createSale(){
  //const urlLink = "http://localhost:8080/sales/"
  const urlLink = "http://3.80.62.114:8080/tiendaVG-0.0.1-SNAPSHOT/sales/" 
  return $.ajax({
    type: "POST",
    url: urlLink + "create",
    headers: {
        'Accept': '*/*',
        'Content-Type': 'application/json'},
    data: JSON.stringify(sale),
    dataType: 'json',
    success: function (data){
      alert("¡La venta se realizó exitosamente!\n(　＾∇＾)/")
      //console.log(data)
    },
    error: function(error){
      console.log("Error en la petición" + error);
    }
  }).done(function(){
    }
  )
}

function createSaleDetails(){
  $.when(getSaleDetailId().then(function(results){
    for (product of productsData){
    getSaleDetail(product);
    //console.log(saleDetail)
    createSaleDetail();
    saleDetailId += 1
  }
    }))
}

function createSaleDetail(){
  //const urlLink = "http://localhost:8080/saleDetails/"
  const urlLink = "http://3.80.62.114:8080/tiendaVG-0.0.1-SNAPSHOT/saleDetails/" 
  return $.ajax({
    type: "POST",
    url: urlLink + "create",
    headers: {
        'Accept': '*/*',
        'Content-Type': 'application/json'},
    data: JSON.stringify(saleDetail),
    dataType: 'json',
    success: function (data){
      //console.log(data)
    },
    error: function(error){
      console.log("Error en la petición" + error);
    }
  }).done(function(){
    }
  )
}
// Agrega una nueva fila con campos para productos
function addRow(){
  table = document.getElementById("sale-table");
  list = document.getElementById("sale-table_body");
  var tr = document.createElement("tr");
  list.appendChild(tr);

  var column1 = document.createElement("td");
  tr.appendChild(column1);
  column1.insertAdjacentHTML('afterbegin',
   '<div class="input-block"><input class="product-code-input" type="number" name="" id="" placeholder="Código del producto"><button class="btn btn-primary" id="get-product-button"><span class="material-icons-outlined">search</span></button></div>');   
   
   var column2 = document.createElement("td");
  tr.appendChild(column2);
  column2.classList.add("product-name");
  column2.innerHTML = "Elige un producto"

   var column3 = document.createElement("td");
  tr.appendChild(column3);

  column3.insertAdjacentHTML('afterbegin',
   '<div><input type="number" name="" id="" class="quantity-input" placeholder="Nº"></div>');

   var column4 = document.createElement("td");
   tr.appendChild(column4);
   column4.classList.add("product-total");
   column4.innerHTML = "Valor total del producto"
}

const formatter = new Intl.NumberFormat('en-US', {
  style: 'currency',
  currency: 'USD',
  maximumFractionDigits: 0,

  // These options are needed to round to whole numbers if that's what you want.
  //minimumFractionDigits: 0, // (this suffices for whole numbers, but will print 2500.10 as $2,500.1)
  //maximumFractionDigits: 0, // (causes 2500.99 to be printed as $2,501)
});