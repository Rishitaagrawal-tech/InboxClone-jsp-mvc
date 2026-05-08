 function getRandomColor() {
      const letters = '0123456789ABCDEF';
      let color = '#';
      for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
      }
      return color;
    }

  function generateRandomCircle() {
    const circle = document.getElementById('circle');
    circle.style.backgroundColor = getRandomColor();
  }
function showemail(){
	const span1 = document.getElementById('emailDisplay');
	span1.style.display = 'inline'; 
}
window.onload = () => {
  generateRandomCircle();
};
