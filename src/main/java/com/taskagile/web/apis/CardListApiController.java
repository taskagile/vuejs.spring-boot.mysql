package com.taskagile.web.apis;

import com.taskagile.domain.application.CardListService;
import com.taskagile.domain.common.security.CurrentUser;
import com.taskagile.domain.model.cardlist.CardList;
import com.taskagile.domain.model.user.SimpleUser;
import com.taskagile.web.payload.AddCardListPayload;
import com.taskagile.web.payload.ChangeCardListPositionsPayload;
import com.taskagile.web.results.AddCardListResult;
import com.taskagile.web.results.ApiResult;
import com.taskagile.web.results.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CardListApiController {

  private CardListService cardListService;

  public CardListApiController(CardListService cardListService) {
    this.cardListService = cardListService;
  }

  @PostMapping("/api/card-lists")
  public ResponseEntity<ApiResult> addCardList(@RequestBody AddCardListPayload payload,
                                               @CurrentUser SimpleUser currentUser) {
    CardList cardList = cardListService.addCardList(payload.toCommand(currentUser.getUserId()));
    return AddCardListResult.build(cardList);
  }

  @PostMapping("/api/card-lists/positions")
  public ResponseEntity<ApiResult> changeCardListPositions(@RequestBody ChangeCardListPositionsPayload payload) {
    cardListService.changePositions(payload.toCommand());
    return Result.ok();
  }
}
