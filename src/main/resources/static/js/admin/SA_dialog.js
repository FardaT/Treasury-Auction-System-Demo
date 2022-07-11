$(document).ready(function () {

    // ADMIN/USERS
    $(".sa_click").click(function (e) {

        // alert("The paragraph was clicked.");

        // https://stackoverflow.com/questions/32164151/sweet-alert-continue-submitting-form-on-confirm

        e.preventDefault();
        var form = $(this).parents('form');

        Swal.fire({
            title: 'Do you want to save the changes?',
            showDenyButton: true,
            showCancelButton: true,
            confirmButtonText: 'Save',
            denyButtonText: `Don't save`,
        }).then((result) => {
            /* Read more about isConfirmed, isDenied below */
            if (result.isConfirmed) {

                form.submit();

                // Swal.fire('Saved!', '', 'success')
            } else if (result.isDenied) {
                Swal.fire('Changes are not saved', '', 'info')
            }
        })

    });

    // AUCTIONLIST
    $(".sa_click_twoButtons").click(function (e) {
        e.preventDefault();
        var form = $(this).parents('form');
        Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, proceed!'
        }).then((result) => {
            if (result.isConfirmed) {
                form.submit();
            }
        })
    });
});