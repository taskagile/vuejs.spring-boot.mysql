package com.taskagile.web.apis;

import com.taskagile.domain.application.CardService;
import com.taskagile.domain.common.security.CurrentUser;
import com.taskagile.domain.model.card.Card;
import com.taskagile.domain.model.user.SimpleUser;
import com.taskagile.web.payload.AddCardPayload;
import com.taskagile.web.payload.ChangeCardPositionsPayload;
import com.taskagile.web.results.AddCardResult;
import com.taskagile.web.results.ApiResult;
import com.taskagile.web.results.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CardApiController {

  private CardService cardService;

  public CardApiController(CardService cardService) {
    this.cardService = cardService;
  }

  @PostMapping("/api/cards")
  public ResponseEntity<ApiResult> addCard(@RequestBody AddCardPayload payload,
                                           @CurrentUser SimpleUser currentUser) {
    Card card = cardService.addCard(payload.toCommand(currentUser.getUserId()));
    return AddCardResult.build(card);
  }

  @PostMapping("/api/cards/positions")
  public ResponseEntity<ApiResult> changeCardPositions(@RequestBody ChangeCardPositionsPayload payload) {
    cardService.changePositions(payload.toCommand());
    return Result.ok();
  }
}
