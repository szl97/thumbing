import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:thumbing/logic/bloc/user/auth_bloc.dart';
import 'package:thumbing/logic/bloc/user/login_bloc.dart';
import 'package:thumbing/logic/state/user/auth_state.dart';
import 'package:thumbing/presentation/pages/home/guid.dart';
import 'package:thumbing/presentation/pages/login/login.dart';

class Initial extends StatelessWidget {
  const Initial({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MultiBlocProvider(
      providers: [
        BlocProvider(create: (context) => LoginBloc()),
        BlocProvider(
          create: (context) => AuthBloc(BlocProvider.of<LoginBloc>(context)),
        )
      ],
      child: InitialView(),
    );
  }
}

class InitialView extends StatelessWidget {
  const InitialView({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<AuthBloc, AuthState>(
      bloc: BlocProvider.of<AuthBloc>(context),
      builder: (context, state) {
        if (state is Authenticated) {
          return Guid();
        } else if (state is UnAuthenticated) {
          return LoginPage();
        }
      },
    );
  }
}
