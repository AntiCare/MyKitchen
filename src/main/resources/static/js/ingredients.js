let update = false;
let name ="";
/**
 * submit form for PUT & POST
 */
document.getElementById("myiForm").addEventListener("submit",async function(event){
    event.preventDefault();
    let ingredient={};
    for(const elem of document.querySelectorAll("input")){
        ingredient[elem.name]=elem.value;
    }
        console.log(ingredient)
    if(update){
        await  updateIngredient(ingredient,name)
    }else {
        await addIngredient(ingredient);
    }

        for(const elem of document.querySelectorAll("input[type=text],input[type=number],input[type=text]")){
            elem.value="";

        }

        update = false;
        name ="";

})


async function deleteIngredient(event){
    var id = parseFloat(event.id);
    console.log("delete ID: "+id);
    await deleteIng(id);
}

/**
 * FetchAPI - delete
 */
async function deleteIng(id){
    try{
        const response = await fetch('http://localhost:8080/ingredients/'+id,{
            method: "DELETE"
        }).then(res=>res.json())
            .then(data=>{
                if(data.toString()==="500"){
                    alert("delete fail!");
                }else if(data.toString()==="200"){
                    alert("delete ok!");
                }
            })
    }catch (e){
        console.error(e);
        alert("Something went wrong!")
    }

}
/**
 * FetchAPI - POST
 */
async function addIngredient(ingredient){
    try{
        const response = await fetch('http://localhost:8080/ingredients',{
            method: "POST",
            headers: {
                'Content-Type': 'Application/json'
            },
            body: JSON.stringify(ingredient)
        });

        await response.json();
        document.getElementById("myiForm").style.display = "none";
    }catch (e){
        console.error(e);
        alert("Something went wrong!")
    }
}

/**
 * FetchAPI - PUT
 */
async function updateIngredient(ingredient,name){
    try{
        const response = await fetch('http://localhost:8080/ingredients/'+name,{
            method: "PUT",
            headers: {
                'Content-Type': 'Application/json'
            },
            body: JSON.stringify(ingredient)
        });

        await response.json();
        document.getElementById("myiForm").style.display = "none";
    }catch (e){
        console.error(e);
        alert("Something went wrong!")
    }
}

function openForm() {
    document.getElementById("myiForm").style.display = "block";
}

function closeForm() {
    document.getElementById("myiForm").style.display = "none";
}
function updateForm(event){
    document.getElementById("myiForm").style.display = "block";
    console.log(event.id)
    update = true;
    name = parseFloat(event.id);
    console.log("name:"+name);
}


document.getElementById("searchBar").addEventListener("submit",async function(event){
    event.preventDefault();
    let search={};
    for(const elem of document.querySelectorAll("input[type=text]")){
        search[elem.name]=elem.value;
    }

    console.log(search);
    var searchBody = [];
    for (var property in search) {
        var encodedKey = encodeURIComponent(property);
        var encodedValue = encodeURIComponent(search[property]);
        searchBody.push(encodedKey + "=" + encodedValue);
    }
    searchBody = searchBody.join("&");

    console.log(searchBody);

    await searchFunction(searchBody);

    for(const elem of document.querySelectorAll("input[type=text]")){
        elem.value="";
    }
})

/**
 * FetchAPI - Get (search)
 */
async function searchFunction(searchBody){
    try{
        const response = await fetch('http://localhost:8080/ingredients/search?'+searchBody,{
            method: "GET",
            headers: {
                'Content-Type': 'Application/json'
            }
        }).then(res=>res.json())
            .then(data=>{
                console.log(data)
            })
    }catch (e){
        console.error(e);
        alert("Something went wrong!")
    }

}
