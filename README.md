<div align="center">

![header](https://capsule-render.vercel.app/api?type=waving&color=0:0f0c29,50:302b63,100:24243e&height=200&section=header&text=CryptoTracker&fontSize=60&fontColor=ffffff&fontAlignY=38&desc=Real-time%20crypto%20alert%20platform&descAlignY=58&descSize=18&animation=fadeIn)

[![Typing SVG](https://readme-typing-svg.demolab.com?font=Fira+Code&size=18&pause=1000&color=7C6AF7&center=true&vCenter=true&width=600&lines=Микросервисная+архитектура+на+Java;Spring+Boot+%2B+Kafka+%2B+Redis+%2B+PostgreSQL;JWT+аутентификация+между+сервисами;Проект+в+активной+разработке)](https://git.io/typing-svg)

<br/>

![Java](https://img.shields.io/badge/Java_23-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Apache Kafka](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
![Redis](https://img.shields.io/badge/Redis_7-DC382D?style=for-the-badge&logo=redis&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL_17-336791?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

![Status](https://img.shields.io/badge/статус-в_разработке-yellow?style=flat-square)

</div>

---

## 💡 О проекте

**CryptoTracker** — платформа для отслеживания криптовалютных цен в реальном времени с системой оповещений.

Пользователь создаёт алерт: *«уведоми меня, если BTC упадёт ниже 60 000$»*. Система следит за ценами каждые 10 секунд и мгновенно отправляет push-уведомление через WebSocket когда порог пробит.

> Проект строится как учебный, но с реальными enterprise-паттернами: event-driven архитектура, distributed locking, JWT авторизация между сервисами, контейнеризация.


---

## Сервисы

<details>
<summary><b>user-service</b> - аутентификация и авторизация</summary>

- Регистрация и логин с возвратом пары токенов
- **Access token** (15 мин) + **Refresh token** (30 дней) в БД
- BCrypt хэширование паролей
- Ротация refresh токенов при каждом обновлении
- Swagger UI: `http://localhost:8081/swagger-ui.html`

</details>

<details>
<summary><b>alert-service</b> - управление алертами</summary>

- `POST /api/v1/alerts` — создать алерт (`coinId`, `targetPrice`, `ABOVE/BELOW`)
- `GET /api/v1/alerts` — получить свои алерты
- `DELETE /api/v1/alerts/{id}` — удалить алерт
- `userId` извлекается из JWT токена — пользователь не может подделать
- Swagger UI: `http://localhost:8082/swagger-ui.html`

</details>

<details>
<summary><b>price-fetcher-service</b> - получение цен</summary>

- Опрашивает CoinGecko API каждые 10 секунд
- Отслеживает валюты указанные в файле конфигурации
- Публикует `PriceUpdateEvent` в Kafka топик `price-updates`
- Stateless — нет базы данных
- `@Scheduled` + `KafkaTemplate`

</details>

<details>
<summary><b>notification-service</b> - уведомления <i></i></summary>

- Kafka consumer топика `price-updates`
- Матчинг цен с алертами пользователей
- **Redis distributed lock** — защита от дублирования уведомлений
- WebSocket push в браузер в реальном времени

</details>

<details>
<summary><b>common</b> - общая библиотека</summary>

- `JwtService` — генерация и валидация токенов
- `TokenAuthenticationFilter` — JWT фильтр для всех сервисов
- `AccountResponse` — principal объект с userId и email
- `PriceUpdateEvent`, `AlertTriggeredEvent` — Kafka DTO события

</details>

---


## Прогресс

- [x] `user-service` — регистрация, логин, JWT, refresh токены
- [x] `alert-service` — CRUD алертов
- [x] `price-fetcher-service` — CoinGecko, Kafka producer
- [x] `common` модуль — JWT, фильтры, Kafka события
- [ ] `notification-service` — Kafka consumer, Redis, WebSocket
- [ ] API Gateway — Spring Cloud Gateway
- [ ] Docker Compose — полный деплой всех сервисов
- [ ] README — финальная документация

---

<div align="center">

![footer](https://capsule-render.vercel.app/api?type=waving&color=0:24243e,50:302b63,100:0f0c29&height=100&section=footer)

</div>
