import 'package:bloc/bloc.dart';
import 'package:flutter/cupertino.dart';
import 'dart:async';
import 'package:thumbing/logic/event/user/auth_event.dart';
import 'package:thumbing/logic/state/user/auth_state.dart';
import 'package:thumbing/logic/bloc/user/login_bloc.dart';
import 'package:thumbing/logic/state/user/login_state.dart';
import 'package:thumbing/data/constant/current_user.dart';

class AuthBloc extends Bloc<AuthEvent, AuthState> {
  @required
  LoginBloc loginBloc;
  StreamSubscription loginSubscription;
  AuthBloc(this.loginBloc) : super(UnAuthenticated()) {
    loginSubscription = loginBloc.listen((state) {
      if (state is LoginSuccess) {
        this.add(FinishLogin(user: state.userInfo));
      }
      if (state is LogoutSuccess) {
        this.add(FinishLogout());
      }
    });
  }
  @override
  Stream<AuthState> mapEventToState(AuthEvent event) async* {
    final currentState = state;
    if (event is FinishLogin) {
      if (currentState is UnAuthenticated) {
        CurrentUser.setUser(event.user);
        yield Authenticated();
      }
    }
    if (event is FinishLogout) {
      if (currentState is Authenticated) {
        CurrentUser.clear();
        yield UnAuthenticated();
      }
    }
  }
}
