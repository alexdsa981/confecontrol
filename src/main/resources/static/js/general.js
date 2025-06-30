
function actualizarHora() {
    const ahora = new Date();
    const horas = String(ahora.getHours()).padStart(2, '0'); // Obtiene las horas y añade un 0 al inicio si es necesario
    const minutos = String(ahora.getMinutes()).padStart(2, '0'); // Obtiene los minutos
    const segundos = String(ahora.getSeconds()).padStart(2, '0'); // Obtiene los segundos

    document.getElementById('horaActual').textContent = `${horas}:${minutos}:${segundos}`;
}
setInterval(actualizarHora, 1000);




const currentPath = window.location.pathname;
const navLinks = document.querySelectorAll('.sidebar .nav-link');
navLinks.forEach(link => {
  // Si el enlace tiene un href que coincide con la URL actual, añades la clase 'active'
  if (link.getAttribute('href') === currentPath) {
    link.classList.add('active');
  } else {
    link.classList.remove('active');
  }
});




    document.addEventListener("DOMContentLoaded", () => {
        const btn = document.querySelector(".notificaciones-btn");
        const contador = document.querySelector(".notificaciones-contador");
        const dropdown = document.querySelector(".notificaciones-dropdown");

        if (!btn || !contador || !dropdown) {
            console.warn('Elementos de notificación no encontrados.');
            return;
        }

        async function actualizarContador() {
            try {
                const response = await fetch('/app/notificaciones');
                if (response.ok) {
                    const notificaciones = await response.json();
                    const noLeidas = notificaciones.filter(n => !n.leido).length;

                    if (noLeidas > 0) {
                        contador.textContent = noLeidas;
                        contador.classList.remove('d-none');
                    } else {
                        reiniciarContador();
                    }
                }
            } catch (error) {
                console.error('Error al actualizar el contador:', error);
            }
        }

        async function cargarNotificaciones() {
            try {
                const response = await fetch('/app/notificaciones');
                if (response.ok) {
                    const notificaciones = await response.json();
                    dropdown.innerHTML = '';

                    if (notificaciones.length > 0) {
                        notificaciones.forEach(n => {
                            const item = document.createElement('li');
                            item.innerHTML = `
                                <a style="text-decoration: none" href="${n.url}" class="notificacion-item" data-id="${n.id}">
                                    <div class="dropdown-item ${n.abierto ? 'notificacion-abierta' : ''}">
                                        <strong>${n.idFormateado}</strong>
                                        <p class="mb-0">${n.descripcion}</p>
                                        <small class="text-muted">${n.fechaFormateada} ${n.horaFormateada} ${n.abierto ? '- Abierto' : ''}</small>
                                    </div>
                                </a>
                            `;
                            dropdown.appendChild(item);
                        });
                    } else {
                        dropdown.innerHTML = `<li><span class="dropdown-item-text text-muted">Sin notificaciones nuevas</span></li>`;
                    }

                    dropdown.querySelectorAll('.notificacion-item').forEach(item => {
                        item.addEventListener('click', async (e) => {
                            const id = e.currentTarget.dataset.id;
                            await marcarNotificacionComoAbierto(id);
                            e.currentTarget.querySelector('.dropdown-item').classList.add('notificacion-abierta');
                        });
                    });
                }
            } catch (error) {
                console.error('Error al cargar notificaciones:', error);
            }
        }

        async function marcarNotificacionComoAbierto(id) {
            try {
                await fetch(`/app/notificaciones/marcar-abierto/${id}`, { method: 'POST' });
            } catch (error) {
                console.error('Error al marcar como abierto:', error);
            }
        }

        async function marcarNotificacionesComoLeidas() {
            try {
                const response = await fetch('/app/notificaciones/marcar-leidas', { method: 'POST' });
                if (response.ok) reiniciarContador();
            } catch (error) {
                console.error('Error al marcar como leídas:', error);
            }
        }

        function reiniciarContador() {
            contador.textContent = '0';
            contador.classList.add('d-none');
        }

        btn.addEventListener('click', async () => {
            await marcarNotificacionesComoLeidas();
            await cargarNotificaciones();
        });

        actualizarContador();
    });