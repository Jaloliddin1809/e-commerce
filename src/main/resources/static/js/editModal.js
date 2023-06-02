$('document').ready(function(){
    $('.table .btn').on('click', function (event){
        event.preventDefault();

        var href= $(this).attr('href');

        $.get(href, function (employee, status){
            $('#editId').val(employee.id);
            $('#editName').val(employee.name);
            $('#editUsername').val(employee.username);
            $('#editPassword').val(employee.password);
        });
        $('#editModal').modal();
    });
});