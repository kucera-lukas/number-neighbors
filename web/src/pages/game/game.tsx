import ErrorText from "../../components/errors/error.text";
import { SERVER_URI } from "../../config/environment";
import PageLayout from "../../layouts/page.layout";
import LocalStorageService from "../../services/local-storage.service";

import { List } from "@mantine/core";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import type { default as GameType } from "../../types/game.type";

const Game = (): JSX.Element => {
  const params = useParams();
  const gameId = params.gameId as string;
  const [game, setGame] = useState<GameType>();
  const [error, setError] = useState<string>();

  useEffect(() => {
    fetch(`${SERVER_URI}/games/${gameId}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${LocalStorageService.get("token") as string}`,
        "Content-Type": "application/json",
      },
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error(res.status.toLocaleString());
        }
        return res.json();
      })
      .then((game: GameType) => {
        setGame(game);
      })
      .catch((error: Error) => setError(error.message));
  }, [gameId]);

  return (
    <PageLayout title="game">
      {game && (
        <div>
          <List>
            {game.players.map((player) => (
              <List.Item key={player.id.toString()}>{player.name}</List.Item>
            ))}
          </List>
        </div>
      )}
      {error && <ErrorText error={error} />}
    </PageLayout>
  );
};

export default Game;
