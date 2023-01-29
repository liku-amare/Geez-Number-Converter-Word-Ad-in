/*
 * Copyright (c) Microsoft Corporation. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */

/* global document, Office, Word */

Office.onReady((info) => {
  if (info.host === Office.HostType.Word) {
    document.getElementById("sideload-msg").style.display = "none";
    document.getElementById("app-body").style.display = "flex";
    document.getElementById("run").onclick = run;
  }
});

async function run() {
  await Word.run(async (context) => {
    /**
     * Insert your Word code here
     */

    // insert a paragraph at the end of the document.
    // const paragraph = context.document.body.insertParagraph("Hello Liku", Word.InsertLocation.end);

    // // change the paragraph color to blue.
    // paragraph.font.color = "blue";

    Office.context.document.getSelectedDataAsync(Office.CoercionType.Text, function (asyncResult) {
      if (asyncResult.status == Office.AsyncResultStatus.Failed) {
        write("Action failed. Error: " + asyncResult.error.message);
      } else {
        var num, converted_num;
        num = asyncResult.value;
        converted_num = toGeez(num);
        // asyncResult.value = converted_num;
        write(converted_num);
        var doc = context.document;
        var originalRange = doc.getSelection();
        // writeGeez(context, converted_num);
        originalRange.insertText(converted_num, "Replace");
      }
    });
    await context.sync();
  });
}

// Function that writes to a div with id='message' on the page.
function write(message) {
  document.getElementById("message").innerText = message;
}

// function writeGeez(context, num) {
//   const paragraph = context.document.body.insertParagraph(num, Word.InsertLocation.before);
//   // change the paragraph color to blue.
//   paragraph.font.color = "blue";
// }

function toGeez(nnum) {
  var DIGITS, HUNDRED, TEN_THOUSAND;
  DIGITS = [
    ["", "፩", "፪", "፫", "፬", "፭", "፮", "፯", "፰", "፱"],
    ["", "፲", "፳", "፴", "፵", "፶", "፷", "፸", "፹", "፺"],
  ];
  HUNDRED = "፻";
  TEN_THOUSAND = "፼";
  var converted, digit_pos, num, q, cur_digit;
  num = nnum.trim();

  // if (num.length > 4) {
  //   return "Too Much";
  // }

  converted = "";
  num = Number.parseInt(num);
  digit_pos = 1;

  while (num !== 0) {
    q = num % 10; 
    // there needs to be some checks on the value of q, for example,
    // if q is 1 in 10000, no need to add 1 at the front;
    // if it is trailing zero of a big number, no need to consider that as well
    cur_digit = digit_pos % 4 == 0 ? 4 : digit_pos % 4;
    if (digit_pos > 4 && cur_digit === 1) {
      converted = TEN_THOUSAND + converted;
    }
    if (cur_digit <= 2) {
      converted = DIGITS[cur_digit - 1][q] + converted;
    } else {
      if (cur_digit === 3) {
        converted = DIGITS[cur_digit - 3][q] + HUNDRED + converted;
      } else {
        converted = DIGITS[cur_digit - 3][q] + converted;
      }
    }

    digit_pos += 1;
    num = Number.parseInt(num / 10);
  }

  return converted;
}
