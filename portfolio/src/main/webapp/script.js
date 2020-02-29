// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings = [
      'Hello world!', 
      '¡Hola Mundo!', 
      '你好，世界！', 
      'Bonjour le monde!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}
//Add random quote
function addRandomQuote() {
  const B99Quotes = [
      '"If I die, turn my tweets into a book." -Gina Linetti', 
      '"The English language can not fully capture the depth and complexity of my thoughts, so I’m incorporating emojis into my speech to better express myself. Winky face." - Ginna Linetti', 
      '"I have zero interest in food. If it were feasible, my diet would consist entirely of flavorless beige smoothies containing all the nutrients required by the human animal." - Captain Ray Holt', 
      '"I only have one dream a year, always on Tax Day. In it, I must file an extension. So, yes, it is best not to have dreams." - Captian Ray Holt'];

  // Pick a random greeting.
  const quote = B99Quotes[Math.floor(Math.random() * B99Quotes.length)];

  // Add it to the page.
  document.getElementById('quote-container1').innerText = quote;
  
}


async function getB99QuoteServlet() {
    //b99 quotes but from servlet instead of just js.
    const response = await fetch('/b99');
    console.log('Fetching a random quote.');
    const quote = await response.text();
    console.log('Adding quote to dom: ' + quote);
    document.getElementById('quote-container2').innerText = quote;

}

/** 
// Counsel log for debugging fetch request
function getB99QuoteServlet() {
  console.log('Fetching a random quote.');

  // The fetch() function returns a Promise because the request is asynchronous.
  const responsePromise = fetch('/b99');

  // When the request is complete, pass the response into handleResponse().
  responsePromise.then(handleResponse);
}

/**
 * Handles response by converting it to text and passing the result to
 * addQuoteToDom().
 
function handleResponse(response) {
  console.log('Handling the response.');

  // response.text() returns a Promise, because the response is a stream of
  // content and not a simple variable.
  const textPromise = response.text();

  // When the response is converted to text, pass the result into the
  // addQuoteToDom() function.
  textPromise.then(addQuoteToDom);
}

// Adds a random quote to the DOM. 
function addQuoteToDom(quote) {
  console.log('Adding quote to dom: ' + quote);

  const quoteContainer = document.getElementById('quote-container2').innerText = quote;
}
**/

async function getHello() {
    //b99 quotes but from servlet instead of just js.
    const response = await fetch('/hello');
    console.log('Fetching hello mannie text.');
    const quote = await response.text();
    console.log('Adding quote to dom: ' + quote);
    document.getElementById('hello-container').innerHTML = quote;

}

