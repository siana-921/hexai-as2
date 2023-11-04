document.addEventListener("DOMContentLoaded", function() {

    console.log("js 잘 불러옴~");

    searchForm.addEventListener("submit", async function(e) {
        e.preventDefault(); //폼 기본 동작 막기

        const form = document.getElementById("searchForm");
        const input = document.getElementById("searchInput");
        const results = document.getElementById("searchResults");

        const category = document.getElementById("searchType").value;
        const val = input.value;

        console.log(category)

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
                div.innerHTML = `
                    <div class="w-full mb-4">
                        <p class="w-full p-3 text-center text-xl bolder bg-slate-300">${item.icao}</p>
                        <div class="w-full">
                            <div class="flex flex-row w-full">
                                <p class="w-1/6 min-w-[100px] border-r border-slate-400">ICAO</p>
                                <p class="flex-1">${item.icao}</p>
                            </div>
                            <div class="flex flex-row w-full">
                                <p class="w-1/6 min-w-[100px] border-r border-slate-400">IATA</p>
                                <p class="flex-1">${item.iata}</p>
                            </div>
                            <div class="flex flex-row w-full">
                                <p class="w-1/6 min-w-[100px] border-r border-slate-400">NAME</p>
                                <p class="flex-1">${item.name}</p>
                            </div>
                            <div class="flex flex-row w-full">
                                <p class="w-1/6 min-w-[100px] border-r border-slate-400">CITY</p>
                                <p class="flex-1">${item.city}</p>
                            </div>
                            <div class="flex flex-row w-full">
                                <p class="w-1/6 min-w-[100px] border-r border-slate-400">STATE</p>
                                <p class="flex-1">${item.state}</p>
                            </div>
                            <div class="flex flex-row w-full">
                                <p class="w-1/6 min-w-[100px] border-r border-slate-400">COUNTRY</p>
                                <p class="flex-1">${item.country}</p>
                            </div>
                            <div class="flex flex-row w-full">
                                <p class="w-1/6 min-w-[100px] border-r border-slate-400">ELEVATION</p>
                                <p class="flex-1">${item.elevation}</p>
                            </div>
                            <div class="flex flex-row w-full">
                                <p class="w-1/6 min-w-[100px] border-r border-slate-400">LAT</p>
                                <p class="flex-1">${item.lat}</p>
                            </div>
                            <div class="flex flex-row w-full">
                                <p class="w-1/6 min-w-[100px] border-r border-slate-400">LON</p>
                                <p class="flex-1">${item.lon}</p>
                            </div>
                            <div class="flex flex-row w-full">
                                <p class="w-1/6 min-w-[100px] border-r border-slate-400">TZ</p>
                                <p class="flex-1">${item.tz}</p>
                            </div>
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

});
