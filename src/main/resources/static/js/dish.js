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


const cardDisplay = document.getElementById("container")
async function getDish(){
    try{
        const response = await fetch('http://localhost:8080/dishes');
        console.log("Get Dishes: "+response);
        const responseJson = await response.json();
        console.log("Get Dishes Json: "+responseJson)
        for(const item of responseJson){
            //create elements
            const card =document.createElement("div")
            const name =document.createElement("h3")
            const category =document.createElement("h3")
            const time =document.createElement("h3")
            const content1 = document.createElement("div")
            const face1 = document.createElement("div")

            const description =document.createElement("p")
            const deleteButton =document.createElement("a")
            const updateButton =document.createElement("a")
            const ingredientButton =document.createElement("a")
            const content2 = document.createElement("div")
            const face2 = document.createElement("div")

            //set class/id/onclick
            deleteButton.id= item.id+"dd"
            deleteButton.addEventListener("click",function (){deleteDish(this)})
            updateButton.id = item.id+"d"
            updateButton.addEventListener("click",function (){updateForm(this)})
            updateButton.type="submit"
            content2.className="content"
            content1.className="content"
            face2.className="face face2"
            face1.className="face face1"
            card.className="card"

            //set innerHTML
            name.innerHTML ="Name: "+item.name
            category.innerHTML="Category: "+item.category
            time.innerHTML="Time: "+item.time
            description.innerHTML="Description: "+item.description
            updateButton.innerHTML="Modify"
            deleteButton.innerHTML="Delete"
            ingredientButton.innerHTML = "View ingredient list"

            //build up
            content1.append(name,category,time);
            face1.append(content1);
            content2.append(description,deleteButton,updateButton,ingredientButton)
            face2.append(content2)
            card.append(face1,face2)
            cardDisplay.append(card)
        }
    }catch (e) {
        alert("Ooops!"+e)
    }
}

getDish();

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
        location.reload();
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
        location.reload();
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
                    location.reload();
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