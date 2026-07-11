
   
    function openHelp() {
        document.getElementById('helpModal').style.display = 'block';
    }
    function closeHelp() {
        document.getElementById('helpModal').style.display = 'none';
    }
    window.onclick = function(event) {
        const modal = document.getElementById('helpModal');
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }
   