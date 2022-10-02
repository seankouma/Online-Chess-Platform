import React, {useEffect, useState} from "react";
import "./board.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMoon } from "@fortawesome/free-solid-svg-icons";
import { faMoon as moon } from "@fortawesome/free-regular-svg-icons";
import { faHourglass } from "@fortawesome/free-solid-svg-icons";
import { faHourglass as hourglass } from "@fortawesome/free-regular-svg-icons";
import { LOG } from "../../utils/constants";
import { getOriginalServerUrl, sendAPIRequest } from "../../utils/restfulAPI";
import {useParams} from "react-router-dom";
const ws = new WebSocket("ws://localhost:8000/boardWebsocket");
export default function Board() {
  const selectedPiece = {
    isSelected: false,
    piece: {},
    row: null,
    column: null,
  };
  const columns = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"];
  const {boardId} = useParams();
  console.log("boardId",boardId)
  useEffect(() => {

    sendBoardRequest(boardId).then((res) => {
      console.log("res", res.board);
      let initialBoard = [];
      for (let i =0; i< res.board.length; i++){
        let initRow = [];
        for (let j =0; j < res.board[i].length; j++){
          let square = {};
          if (res.board[i][j] !== null){
            square.pieceType = res.board[i][j]
            square.piecePresent = true;
          } else {
            square.pieceType = "";
            square.piecePresent = false;
          }
          if ((i==0 || i == 11 || j==0 || j==11)){
            if ((i==0 && j==0) || (i==11 && j==11)){
              square.boxColor = "black"
            } else if((i==0 && j==11) || (i==11 && j==0)) {
              square.boxColor = "white"
            } else {
              square.boxColor = "dark-black"
            }
          } else {
            if ((i+j)%2 == 0){
              square.boxColor = "black"
            } else {
              square.boxColor = "white"
            }
          }
          initRow.push(square);
        }
        initialBoard.push(initRow);
      }
      setChessboard([...initialBoard]);
    })
  },[]);

    console.log("ws", ws);
    ws.onopen = () => {
      console.log("connected websocket board component");
      try {
        ws.send(JSON.stringify({
          type: "Connected",
          sender: getCookie('cs414t16user'),
          msg: "Connection successful"
        })) //send data to the server
      } catch (error) {
        console.log(error) // catch error
      }
    };
    ws.onclose = e => {
      console.log(
          'Socket is closed. Reconnect will be attempted i',
          e.reason
      );
      //this.connect();
    };
    ws.onerror = err => {
      console.error(
          err.message,
      );
      ws.close();
    };
    ws.onmessage = evt => {
      console.log("message", JSON.parse(evt.data));
      let receivedEvent = JSON.parse(evt.data);
      let board = [...chessboard];
        board[receivedEvent.toRow][receivedEvent.toColumn].piecePresent = board[receivedEvent.fromRow][receivedEvent.fromColumn].piecePresent;
        board[receivedEvent.toRow][receivedEvent.toColumn].pieceType = board[receivedEvent.fromRow][receivedEvent.fromColumn].pieceType;
        board[receivedEvent.fromRow][receivedEvent.fromColumn].piecePresent = false;
        board[receivedEvent.fromRow][receivedEvent.fromColumn].pieceType = "";
        setChessboard([...board]);
        board[receivedEvent.fromRow][receivedEvent.fromColumn].isSelected = false;
    }
  const [chessboard, setChessboard] = useState([]);
  //const [WS, setWS] = useState(null);

  function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for(let i = 0; i <ca.length; i++) {
      let c = ca[i];
      while (c.charAt(0) == ' ') {
        c = c.substring(1);
      }
      if (c.indexOf(name) == 0) {
        return c.substring(name.length, c.length);
      }
    }
    return "";
  }
  function onButtonClick(row, column) {
    // `current` points to the mounted text input element

    if (!selectedPiece.isSelected) {
      if (chessboard[row][column].piecePresent) {
        selectedPiece.isSelected = true;
        selectedPiece.piece = chessboard[row][column];
        selectedPiece.row = row;
        selectedPiece.column = column;
      }
    } else {
      sendMoveRequest(
        selectedPiece.row,
        selectedPiece.column,
        row,
        column,
        0,
          getCookie('cs414t16user')
      ).then((res) => {
        console.log("res", res);
        if (res.validMove) {
          chessboard[row][column].piecePresent =
            selectedPiece.piece.piecePresent;
          chessboard[row][column].pieceType = selectedPiece.piece.pieceType;
          chessboard[selectedPiece.row][
            selectedPiece.column
          ].piecePresent = false;
          chessboard[selectedPiece.row][selectedPiece.column].pieceType = "";
          setChessboard([...chessboard]);
          selectedPiece.isSelected = false;
          selectedPiece.piece = {};
        } else {
          console.log("Illegal Move");
          selectedPiece.isSelected = false;
          selectedPiece.piece = {};
          selectedPiece.row = null;
          selectedPiece.column =  null;
        }
      });
    }
  }

  function setBoard(){
    useEffect(()=> {
      let res = sendBoardRequest(0);
      console.log("res", res);
    },[])
  }

  return (
      <>
    <div className="row">
      <div className="col-lg-12">
        <div className="chessboard">
          {chessboard.map((items, index) => {
            return (
              <ol>
                {items.map((subItems, sIndex) => {
                  if (subItems.piecePresent) {
                    if (subItems.pieceType === "blackWizard") {
                      return (
                        <div
                          className={subItems.boxColor}
                          onClick={() => onButtonClick(index, sIndex)}
                        >
                          <FontAwesomeIcon icon={faMoon} flip="horizontal" />
                        </div>
                      );
                    } else if (subItems.pieceType === "whiteWizard") {
                      return (
                        <div
                          className={subItems.boxColor}
                          onClick={() => onButtonClick(index, sIndex)}
                        >
                          <FontAwesomeIcon icon={moon} flip="horizontal" />
                        </div>
                      );
                    } else if (subItems.pieceType === "blackChampion") {
                      return (
                        <div
                          className={subItems.boxColor}
                          onClick={() => onButtonClick(index, sIndex)}
                        >
                          <FontAwesomeIcon icon={faHourglass} />
                        </div>
                      );
                    } else if (subItems.pieceType === "whiteChampion") {
                      return (
                        <div
                          className={subItems.boxColor}
                          onClick={() => onButtonClick(index, sIndex)}
                        >
                          <FontAwesomeIcon icon={hourglass} />
                        </div>
                      );
                    } else {
                      return (
                        <div
                          className={
                            subItems.boxColor + " " + subItems.pieceType
                          }
                          onClick={() => onButtonClick(index, sIndex)}
                        ></div>
                      );
                    }
                  } else {
                    return (
                      <div
                        className={subItems.boxColor}
                        onClick={() => onButtonClick(index, sIndex)}
                      ></div>
                    );
                  }
                })}
              </ol>
            );
          })}
        </div>
      </div>
    </div>
        </>
  );
}



async function sendMoveRequest(fromRow, fromColumn, toRow, toColumn, boardID, userID) {
  const requestResponse = await sendAPIRequest(
    {
      requestType: "move",
      fromRow: fromRow,
      fromColumn: fromColumn,
      toRow: toRow,
      toColumn: toColumn,
      boardID: boardID,
      userID: userID
    },
    getOriginalServerUrl()
  );
  if (requestResponse) {
    return requestResponse;
  } else {
    LOG.info("Failed to get move response");
  }
}

async function sendBoardRequest(boardID) {
  const requestResponse = await sendAPIRequest(
    {
      requestType: "board",
      boardID: boardID
    },
    getOriginalServerUrl()
  );
  if (requestResponse) {
    return requestResponse;
  } else {
    LOG.info("Failed to get move response");
  }
}
