let update = false;
let name ="";
document.getElementById("myfForm").addEventListener("submit",async function(event){
    event.preventDefault();
    let dish={};
    for(const elem of document.querySelectorAll("input[type=text],input[type=text],input[type=number],input[type=text]")){
        dish[elem.name]=elem.value;
    }
    console.log(dish)
    if(update){
        await  updateFood(dish,name)
    }else {
        await  addFood(dish);
    }



    for(const elem of document.querySelectorAll("input[type=text],input[type=text],input[type=number],input[type=text]")){
        elem.value="";
    }
    update = false;
    name ="";

})


async function addFood(dish){
    try{
        const response = await fetch('http://localhost:8080/dishes',{
            method: "POST",
            headers: {
                'Content-Type': 'Application/json'
            },
            body: JSON.stringify(dish)
        });

        await response.json();
        document.getElementById("myfForm").style.display = "none";
    }catch (e){
        console.error(e);
        alert("Something went wrong!")
    }

}

async function updateFood(dish,name){
    try{
        const response = await fetch('http://localhost:8080/dishes/'+name,{
            method: "PUT",
            headers: {
                'Content-Type': 'Application/json'
            },
            body: JSON.stringify(dish)
        });

        await response.json();
        document.getElementById("myfForm").style.display = "none";
    }catch (e){
        console.error(e);
        alert("Something went wrong!")
    }
}

async function deleteFood(id){
    try{
        const response = await fetch('http://localhost:8080/dishes/'+id,{
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

async function deleteDish(event){
    var id = parseFloat(event.id);
    console.log("delete ID: "+id);
    await deleteFood(id);
}

function updateForm(event){
    document.getElementById("myfForm").style.display = "block";
    update = true;
    name = parseFloat(event.id);
    console.log(name)
}

function openForm() {
    document.getElementById("myfForm").style.display = "block";
}

function closeForm() {
    document.getElementById("myfForm").style.display = "none";
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


async function searchFunction(searchBody){
    try{
        const response = await fetch('http://localhost:8080/dishes/search?'+searchBody,{
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