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

//var urlLink = "http://localhost:8080/products/" // Link para la conexión con el API rest
var urlLink = "http://3.80.62.114:8080/tiendaVG-0.0.1-SNAPSHOT/products/" // Link para la conexión con el API rest
var file = document.getElementById("products-file");
var uploadText = document.getElementById("upload-text");

//Para el archivo
file.addEventListener("change", function(){
    if (file.value) {
      if (file.value.split('.').pop() != "csv") {
        uploadText.innerHTML = "Seleccionar archivo (.csv)"
        alert("El archivo debe ser CSV (separado por comas)\nPor favor carga un archivo válido (⌒_⌒;)")
        file.value = null;
      }
      else{
        uploadText.innerHTML = file.value.match(/[\/\\]([\w\d\s\.\-\(\)]+)$/)[1];
      }
    }
    else{
        uploadText.innerHTML = "Seleccionar archivo"
    }
});

// Cuando se presiona el botón de Cargar
$('.upload-label').on('click', '#upload-button', function(){
  if(file.value) {
    var fileList = file.files;
    productsFile = fileList[0];
    readFile(productsFile);
  }
  else {
    alert("Primero debes cargar un archivo CSV")
  }
})

function readFile(file){
  var reader = new FileReader(); 
  reader.readAsText(file);
  reader.onload = function(event){
    var csv = event.target.result;
    var headers = "productId,productName,providerNit,purchasePrice,purchaseVAT,salePrice\n";
    var productsString = headers + csv;
    var productsData = Object.values(convertIntObj(csvToArray(productsString)));
    addProducts(productsData);
  }
}

// CSV to Array 
function csvToArray(str, delimiter = ",") {
  // slice from start of text to the first \n index
  // use split to create an array from string by delimiter
  const headers = str.slice(0, str.indexOf("\n")).split(delimiter);

  // slice from \n index + 1 to the end of the text
  // use split to create an array of each csv value row
  const rows = str.slice(str.indexOf("\n") + 1).split("\n");

  // Map the rows
  // split values from each row into an array
  // use headers.reduce to create an object
  // object properties derived from headers:values
  // the object passed as an element of the array
  const arr = rows.map(function (row) {
    const values = row.split(delimiter);
    const el = headers.reduce(function (object, header, index) {
      object[header] = values[index];
      return object;
    }, {});
    return el;
  });

  // return the array
  return arr;
}

// Sacado de internet para convertir las String, generadas antes, en enteros
function convertIntObj(obj) {
  const res = {}
  for (const key in obj) {
    res[key] = {};
    for (const prop in obj[key]) {
      const parsed = parseInt(obj[key][prop], 10);
      res[key][prop] = isNaN(parsed) ? obj[key][prop] : parsed;
    }
  }
  return res;
}
 
// Para añadir varios Productos que vienen en el archivo
function addProducts(data){
  data.forEach(function(product) {
    addProduct(product);
  })
  setTimeout(() => {alert("¡Productos agregados exitosamente! <(￣︶￣)>")
  uploadText.innerHTML = "Seleccionar otro archivo"
  }, 3000);
  file.value = null;
}

// Tabla dinámica
var productTable = $("#product_table").DataTable( {
    ajax: {
      type: "POST",
      url: urlLink + "list", //ruta de la API consultaremos.
      dataSrc: ''
    },
    columns: [
        { data: 'productId' },
        { data: 'productName' },
        { data: 'providerNit' },
        { data: 'purchaseVAT' },
        { data: 'purchasePrice' },
        { data: 'salePrice' }
      ],
      language: {
        "decimal":        "",
        "emptyTable":     "No hay datos disponibles en la tabla",
        "info":           "Mostrando _START_ a _END_ de _TOTAL_ entradas",
        "infoEmpty":      "Mostrando 0 a 0 de 0 entradas",
        "infoFiltered":   "(filtrado de _MAX_ entradas totales)",
        "infoPostFix":    "",
        "thousands":      ",",
        "lengthMenu":     "Mostrar _MENU_ entradas",
        "loadingRecords": "Cargando...",
        "processing":     "Procesando...",
        "search":         "Buscar:",
        "zeroRecords":    "No se encontró ningun elemento",
        "paginate": {
            "first":      "Primera",
            "last":       "Última",
            "next":       "Siguiente",
            "previous":   "Anterior"
        },
        "aria": {
            "sortAscending":  ": activate to sort column ascending",
            "sortDescending": ": activate to sort column descending"
        }
  }
});


//Para buscar a un producto
$('form').on('click', '.search-button', function(){
  productId = document.getElementById("input1").value;
  $.ajax({
    type: "GET",
    url: urlLink + productId,

    error: function(){
      console.log("Error en la petición");
      productTable.ajax.reload(null, false);
    }
  }).done(function(data){
    if (data.productId == undefined){
      alert("El producto no existe")
    }
    else{
      printOne(data);
    }
  })
})

//Crear un nuevo producto
$('form').on('click', '.create-button', function(){
  newProduct = getProductData();
  console.log(newProduct);
  addProduct(newProduct);
})

function addProduct(newProduct){
  $.ajax({
    type: "POST",
    url: urlLink + "create",
    headers: {
        'Accept': '*/*',
        'Content-Type': 'application/json'},
    data: JSON.stringify(newProduct),
    dataType: 'json',
    success: function (responseData){
      console.log(responseData)
      productTable.ajax.reload(null, false);
    },
    error: function(error){
      console.log("Error en la petición" + error);
    }
  }).done(function(){
      clearInputs();
    }
  )
}
//Actualizar un usuario
$('form').on('click', '.update-button', function(){
  newProduct = getProductData();
  $.ajax({
    type: "PUT",
    url: urlLink + "update",
    headers: {
        'Accept': '*/*',
        'Content-Type': 'application/json'},
    data: JSON.stringify(newProduct),
    dataType: 'json',
    success: function (responseData){
      console.log(responseData)
      productTable.ajax.reload(null, false);
    },
    error: function(error){
      console.log("Error en la petición" + error);
    }
  }).done(function(){
      clearInputs();
    }
  )
})

//Eliminar Usuario
$('form').on('click', '.delete-button', function(){
  productId = document.getElementById("input1").value;
  $.ajax({
    type: "DELETE",
    url: urlLink + productId,

    error: function(){
      alert("Error en la petición");

    }
  }).done(function(){
    //alert("Se eliminó al usuario");
    productTable.ajax.reload(null, false);
    clearInputs();
  })
})

// Obtiene los datos del formulario y crea un objeto
function getProductData(){
  var newProduct = new Object;
  newProduct.productId = +document.getElementById("input1").value;
  newProduct.productName = document.getElementById("input2").value;
  newProduct.providerNit = +document.getElementById("input3").value;
  newProduct.purchaseVAT = +document.getElementById("input4").value;
  newProduct.purchasePrice = +document.getElementById("input5").value; 
  newProduct.salePrice = +document.getElementById("input6").value; 
  //console.log(newUser)
  return newProduct;
  
}

// Limpia los datos del formulario
function clearInputs(){
  var elements = document.getElementsByClassName('input');
      for (var ii=0; ii < elements.length; ii++) {
          elements[ii].value = "";
      }
}


// Agrega solo un Usuario al HTML
function printOne(data){
  lista = document.getElementById("user_table_body");
  lista.innerHTML = '';
  var tr = document.createElement("tr");
  var trId = document.createAttribute("id");
  trId.value = data.providerNit;
  tr.setAttributeNode(trId);
  var columna1 = document.createElement("td");
  columna1.innerHTML = data.productId;
  var columna2 = document.createElement("td");
  columna2.innerHTML = data.productName;
  var columna3 = document.createElement("td");
  columna3.innerHTML = data.providerNit;
  var columna4 = document.createElement("td");
  columna4.innerHTML = data.purchaseVAT;
  var columna5 = document.createElement("td");
  columna5.innerHTML = data.purchasePrice;
  var columna6 = document.createElement("td");
  columna6.innerHTML = data.salePrice;
  

  lista.appendChild(tr);
  tr.appendChild(columna1);
  tr.appendChild(columna2);
  tr.appendChild(columna3);
  tr.appendChild(columna4);
  tr.appendChild(columna5);
  tr.appendChild(columna6);
}

