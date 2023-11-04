document.addEventListener("DOMContentLoaded", function() {

    searchForm.addEventListener("submit", async function(e) {
        e.preventDefault(); //폼 기본 동작 막기

        const form = document.getElementById("searchForm");
        const input = document.getElementById("searchInput");
        const results = document.getElementById("searchResults");

        const category = document.getElementById("searchType").value;
        const val = input.value;

        if(category==="none" || !val){
            results.textContent="검색 조건과 검색어를 입력해 주세요.";
            return 0;
        }

        results.innerHTML = "";

        const query = {
            params: {
                category: category,
                val: val
            }
        }

        console.log(category, val);

        try {
            const response = await axios.post("/search", null, query);
            console.log(response.data);
            console.log(response);
            console.log(typeof response.data);

            const searchedArray = getValuesArray(response.data);

            searchedArray.forEach(item => {
                const div = document.createElement("div");
                const searchedValueHTML = valueToHTML(item);

                div.innerHTML = `
                    <div class="w-full mb-4">
                        <p class="w-full p-3 text-center text-xl bolder bg-slate-300">${item.icao}</p>
                        <div class="w-full">
                            ${searchedValueHTML}
                        </div>
                    </div>
                `;
                results.appendChild(div);
            });

            //const jsonText = JSON.stringify(response.data, null, 2);
            //results.textContent = jsonText;

        } catch (error) {
            console.error("에러 발생:", error);
        }
    });

    const getValuesArray = (searchedData) => {
        const res = Object.values(searchedData);

        console.log(res);
        return res;
    }

    const valueToHTML = (item) => {
        const keys = Object.keys(item);
        let res = "";
        keys.forEach((key)=>{
            res +=
                `<div class="flex flex-row w-full">
                    <p class="w-1/6 min-w-[100px] border-r border-slate-400 p-2">${key.toUpperCase()}</p>
                    <p class="flex-1 p-2">${item[key]}</p>
                </div>`
        });
        return res;
    }

});
