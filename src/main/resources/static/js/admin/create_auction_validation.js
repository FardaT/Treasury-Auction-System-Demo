
function populate(s1,s2)
{
    var s1 = document.getElementById(s1)
    var s2 = document.getElementById(s2)
    s2.innerHTML = "";
    if (s1.value == "T-Bill") {
        var optionArray = ["4-week|4-week", "8-week|8-week", "13-week|13-week", "26-week|26-week", "52-week|52-week"];
    } else if (s1.value == "T-Note") {
        var optionArray = ["2-year|2-year", "3-year|3-year", "5-year|5-year", "7-year|7-year", "10-year|10-year"];
    } else {
        var optionArray = ["20-year|20-year", "30-year|30-year"];
    }
    for (var option in optionArray) {
        var pair = optionArray[option].split("|")
        var term = document.createElement("option")
        term.value = pair[0]
        term.innerHTML = pair[1]
        s2.options.add(term)
    }
}
//get the saved value function - return the value of "v" from localStorage.
function getSavedValue  (v){
    if (!localStorage.getItem(v)) {
        return "";// You can change this to your defualt value.
    }
    document.getElementById(v).value = localStorage.getItem(v);
}

let start_date = document.querySelector('#auctionStartDate');
let issue_date = document.querySelector('#issueDate');
let start_time = document.querySelector('#auctionStartTime');
let end_time = document.querySelector('#auctionEndTime');
let error = document.querySelector('#error');
let button = document.querySelector('#send_button');

window.onload = function(){

    let today = new Date();
    let dd = String(today.getDate()).padStart(2, '0');
    let mm = String(today.getMonth() + 1).padStart(2, '0');
    let yyyy = today.getFullYear();
    today = yyyy + '-' + mm + '-' + dd;
    start_date.setAttribute("min", today);
    issue_date.setAttribute("min", today);
    if(localStorage.getItem(start_date.id) != null){
        start_date.value = localStorage.getItem(start_date.id);
    }
    if(localStorage.getItem(issue_date.id) != null){
        issue_date.value = localStorage.getItem(issue_date.id);
    }
    if(localStorage.getItem(end_time.id) != null){
        end_time.value = localStorage.getItem(end_time.id);
    }
    if(localStorage.getItem(start_time.id) != null){
        start_time.value = localStorage.getItem(start_time.id);
    }
}

start_date.addEventListener("change", ()=>{
    issue_date.setAttribute("min", start_date.value);
    localStorage.setItem(start_date.id,start_date.value);
})
issue_date.addEventListener("change", ()=>{
    start_date.setAttribute("max", issue_date.value);
    localStorage.setItem(issue_date.id,issue_date.value);
})
start_time.addEventListener("change", ()=>{
    end_time.setAttribute("min", start_time.value);
    localStorage.setItem(start_time.id,start_time.value);
})
end_time.addEventListener("change", ()=>{
    start_time.setAttribute("max", end_time.value);
    localStorage.setItem(end_time.id,end_time.value);
})
issue_date.addEventListener("change", ()=>{
    if(issue_date.value < start_date.value) {
        error.innerHTML = "error"
        button.disabled = true;
    } else {
        error.innerHTML = ""
        button.disabled = false;
    }
})
$(".sa_create_auction_confirmation").click(function (e) {
    e.preventDefault();
    let form = $(this).parents('form');
    let cancel_form = document.getElementById("cancel-auction-creation")
    Swal.fire({
        title: 'Do you wish to submit this auction?',
        showDenyButton: true,
        showCancelButton: true,
        showCloseButton:true,
        confirmButtonText: 'Submit',
        denyButtonText: `Delete changes`,
    }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
/*            Swal.fire({
                icon: 'success',
                title: 'Auction has been submitted',
                showConfirmButton: false,
                timer: 1500})
            sleep(1500).then(() => {form.submit()
            });*/
            form.submit();
            localStorage.removeItem(start_date.id);
            localStorage.removeItem(issue_date.id);
            localStorage.removeItem(end_time.id);
            localStorage.removeItem(start_time.id);
        } else if (result.isDenied) {
            cancel_form.submit();
            localStorage.removeItem(start_date.id);
            localStorage.removeItem(issue_date.id);
            localStorage.removeItem(end_time.id);
            localStorage.removeItem(start_time.id);
        }
    })
});
function sleep (time) {
    return new Promise((resolve) => setTimeout(resolve, time));
}