document.addEventListener("DOMContentLoaded", function() {

    console.log("js 잘 불러옴~");

    searchForm.addEventListener("submit", async function(e) {
        e.preventDefault(); //폼 기본 동작 막기

        const form = document.getElementById("searchForm");
        const input = document.getElementById("searchInput");
        const results = document.getElementById("searchResults");
        const category = document.getElementById("searchType").value;

        const val = input.value;

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
            console.log("?dpd");

            const jsonText = JSON.stringify(response.data, null, 2);

            searchResults.textContent = jsonText;

        } catch (error) {
            console.error("에러 발생:", error);
        }
    });
});
