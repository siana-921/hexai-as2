document.addEventListener("DOMContentLoaded", function() {

    console.log("js 잘 불러옴~");
    const searchForm = document.getElementById("searchForm");
    const searchInput = document.getElementById("searchInput");
    const searchResults = document.getElementById("searchResults");

    searchForm.addEventListener("submit", async function(e) {
        e.preventDefault(); //폼 기본 동작 막기
        const searchTerm = searchInput.value;

        const requestOptions = {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: "searchTerm=" + searchTerm
        };

        try {
            const response = await axios.post("/search");
            console.log(response);

            const jsonText = JSON.stringify(response.data, null, 2);

            searchResults.textContent = jsonText;

        } catch (error) {
            console.error("에러 발생:", error);
        }
    });
});
