// https://www.tutorialrepublic.com/faq/how-to-get-the-value-of-selected-option-in-a-select-box-using-jquery.php
// https://stackoverflow.com/questions/9680037/how-do-i-select-an-element-with-its-name-attribute-in-jquery
// https://stackoverflow.com/questions/36720585/jquery-create-dynamic-name-selector
$(document).ready(function () {

    // how many fields do we have?
    var count = 0;
    $(".isCompetitive").each(function (index) {
        count++;
    });
    // console.log(count);

    // which row was selected? (index)
    $(".isCompetitive").click(function () {
        var nameOfSelectBox = $(this).attr("name");
        // console.log(nameOfSelectBox);

        // get the index
        var index = nameOfSelectBox.substring(0, 10); // bidDTOS[1]
        // console.log(index);

        // get the value
        var value = $(this).children("option:selected").val();
        // console.log(value);

        // disable
        if (value == 0) {
            $('[name="' + index + '.rate' + '"]').prop("disabled", true);
        }
        if (value == 1) {
            $('[name="' + index + '.rate' + '"]').prop("disabled", false);
        }
    });

});