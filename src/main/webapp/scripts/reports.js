let reportsTabeData = [];
let reportsData = [];
let reportsTable;
let customerName;

$( document ).ready(function() {
    $.when(getUsers().done(function(){
        console.log(reportsData)
        console.log(getDataTables(reportsData))
    }));
});

function getUsers(){
    //var urlLink = "http://localhost:8080/sales/"
	var urlLink = "http://3.80.62.114:8080/tiendaVG-0.0.1-SNAPSHOT/sales/"
    return $.ajax({
        type: "POST",
        url: urlLink + "list", //ruta de la API consultaremos.

        success: function(data) {
          reportsData = data;
      }});
}

function getDataTables(data) {
    const dataTables = new Map();
    for (const row of data) {
        const table = dataTables.get(row.userIdCard);
        if (table) {
            table.total += row.saleValue;
        } else {
            dataTables.set(row.userIdCard, {
                userIdCard: row.userIdCard,
                userName: getName(row.userIdCard),
                total: row.saleValue,
            });
        }
    }
    return Array.from(dataTables, ([key, val]) => val);
    //return dataTables;    

}

function getName(customerIdCard){
    let localName;
    $.when(searchCustomer(customerIdCard).done(function(){
        localName = customerName
        return localName;
    }));
}

function searchCustomer(customerIdCard){
    //var urlLink = "http://localhost:8080/customers/" 
	var urlLink = "http://3.80.62.114:8080/tiendaVG-0.0.1-SNAPSHOT/customers/"
    return $.ajax({
        type: "GET",
        url: urlLink + customerIdCard.toString(),

        error: function(){
        console.log("Error en la petición");
        }
    }).done(function(data){
       
        customerName = data.customerName;
        console.log(customerName)
        
    })
}

function createTable(data){

    var reportsTable = $("#user_table").DataTable( {
        data,
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
}