document.addEventListener("DOMContentLoaded", function() {

    searchForm.addEventListener("submit", async function(e) {
        e.preventDefault(); //폼 기본 동작 막기..

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

        try {
            const response = await axios.post("/search", null, query);

            const searchedArray = getValuesArray(response.data);

            searchedArray.forEach(item => {
                const div = document.createElement("div");
                const searchedValueHTML = valueToHTML(item);

                div.innerHTML = `
                    <div class="w-full mb-4 border-b-4 border-slate-200">
                        <p class="w-full p-3 text-center text-xl bolder bg-slate-200">${item.icao}</p>
                        <div class="w-full">
                            ${searchedValueHTML}
                        </div>
                    </div>
                `;
                results.appendChild(div);
            });

        } catch (error) {
            console.error("에러 발생:", error);
        }
    });

    const getValuesArray = (searchedData) => {
        const res = Object.values(searchedData);
        return res;
    }

    const valueToHTML = (item) => {
        const keys = Object.keys(item);
        let res = "";
        keys.forEach((key)=>{
            res +=
                `<div class="flex flex-row w-full">
                    <p class="w-1/6 min-w-[100px] border-r border-slate-200 p-2">${key.toUpperCase()}</p>
                    <p class="flex-1 p-2">${item[key]}</p>
                </div>`
        });
        return res;
    }

});
