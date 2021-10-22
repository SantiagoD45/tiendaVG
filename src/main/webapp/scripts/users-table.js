sessionStorage.setItem("lastPage", window.location.href);
checkSession()

// Para revisar si ya se ha iniciado sesión
function checkSession(){
    //console.log(sessionStorage.getItem("session"))
    if (sessionStorage.getItem("session") == null || sessionStorage.getItem("session") == "none"){
        alert("Primero debes iniciar sesión")
        window.location.href = "index.html";
    }
}

//var urlLink = "http://localhost:8080/users/" // Link para la conexión con el API rest
var urlLink = "http://3.80.62.114:8080/tiendaVG-0.0.1-SNAPSHOT/users/" // Link para la conexión con el API rest

var userTable = $("#user_table").DataTable( {
    ajax: {
      type: "POST",
      url: urlLink + "list", //ruta de la API consultaremos.
      dataSrc: ''
    },
    columns: [
        { data: 'userIdCard' },
        { data: 'userEmail' },
        { data: 'userName' },
        { data: 'password' },
        { data: 'user' }
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


//Para buscar a un usuario
$('form').on('click', '.search-button', function(){
  userId = document.getElementById("input1").value;
  $.ajax({
    type: "GET",
    url: urlLink + userId,

    error: function(){
      console.log("Error en la petición");
      userTable.ajax.reload(null, false);
    }
  }).done(function(data){
    if (data.userIdCard == undefined){
      alert("El usuario no existe")
    }
    else{
      printOne(data);
    }
  })
})

//Crear un nuevo ususario
$('form').on('click', '.create-button', function(){
  var newUser = getUserData();
  $.ajax({
    type: "POST",
    url: urlLink + "create",
    headers: {
        'Accept': '*/*',
        'Content-Type': 'application/json'},
    data: JSON.stringify(newUser),
    dataType: 'json',
    success: function (data){
      userTable.ajax.reload(null, false);
      //console.log(data)
    },
    error: function(error){
      console.log("Error en la petición" + error);
    }
  }).done(function(){
      clearInputs();
    }
  )
})

//Actualizar un usuario
$('form').on('click', '.update-button', function(){
  var newUser = getUserData();
  $.ajax({
    type: "PUT",
    url: urlLink + "update",
    headers: {
        'Accept': '*/*',
        'Content-Type': 'application/json'},
    data: JSON.stringify(newUser),
    dataType: 'json',
    success: function (responseData){
      userTable.ajax.reload(null, false);
      console.log(responseData)
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
  userId = document.getElementById("input1").value;
  $.ajax({
    type: "DELETE",
    url: urlLink + userId,

    error: function(){
      alert("Error en la petición");
      
    }
  }).done(function(){
    //alert("Se eliminó al usuario");
    userTable.ajax.reload(null, false);
    clearInputs();
  })
})

// Obtiene los datos del formulario y crea un objeto
function getUserData(){
  var newUser = new Object;
  newUser.userIdCard = +document.getElementById("input1").value;
  newUser.userName = document.getElementById("input2").value;
  newUser.userEmail = document.getElementById("input3").value;
  newUser.user = document.getElementById("input4").value;
  newUser.password = document.getElementById("input5").value;
  //console.log(newUser)
  return newUser;
  
}

// Limpia los datos del formulario
function clearInputs(){
  var elements = document.getElementsByClassName('input');
      for (var i=0; i < elements.length; i++) {
          elements[i].value = "";
      }
}

// Agrega solo un Usuario al HTML
function printOne(data){
  table = document.getElementById("user_table");
  lista = document.getElementById("user_table_body");
  lista.innerHTML = '';
  var tr = document.createElement("tr");
  var trId = document.createAttribute("id");
  trId.value = data.userIdCard;
  tr.setAttributeNode(trId);
  var columna1 = document.createElement("td");
  columna1.innerHTML = data.userIdCard;
  var columna2 = document.createElement("td");
  columna2.innerHTML = data.userEmail;
  var columna3 = document.createElement("td");
  columna3.innerHTML = data.userName;
  var columna4 = document.createElement("td");
  columna4.innerHTML = data.password;
  var columna5 = document.createElement("td");
  columna5.innerHTML = data.user;
  

  lista.appendChild(tr);
  tr.appendChild(columna1);
  tr.appendChild(columna2);
  tr.appendChild(columna3);
  tr.appendChild(columna4);
  tr.appendChild(columna5);

}

