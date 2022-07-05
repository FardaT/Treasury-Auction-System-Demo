
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